/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ericho.coupleshare.mvp;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


import com.ericho.coupleshare.mvp.data.LocationDataSource;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import timber.log.Timber;


/**
 * Concrete implementation to load tasks from the data sources into a cache.
 * <p>
 * For simplicity, this implements a dumb synchronisation between locally persisted data and data
 * obtained from the server, by using the remote data source only if the local database doesn't
 * exist or is empty.
 */
public class LocationsRepository implements LocationDataSource {

    private static LocationsRepository INSTANCE = null;

    private final LocationDataSource mLocationsRemoteDataSource;

    private final LocationDataSource mLocationsLocalDataSource;

    /**
     * This variable has package local visibility so it can be accessed from tests.
     */
    Map<Integer, Location> mCachedTasks;

    /**
     * Marks the cache as invalid, to force an update the next time data is requested. This variable
     * has package local visibility so it can be accessed from tests.
     */
    boolean mCacheIsDirty = false;

    // Prevent direct instantiation.
    private LocationsRepository(@NonNull LocationDataSource mLocationsRemoteDataSource,
                                @NonNull LocationDataSource mLocationsLocalDataSource) {
        this.mLocationsRemoteDataSource = mLocationsRemoteDataSource;
        this.mLocationsLocalDataSource = mLocationsLocalDataSource;
    }


    public static LocationsRepository getInstance(LocationDataSource tasksRemoteDataSource,
                                                  LocationDataSource tasksLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new LocationsRepository(tasksRemoteDataSource, tasksLocalDataSource);
        }
        return INSTANCE;
    }


    public static void destroyInstance() {
        INSTANCE = null;
    }

    /**
     * Gets tasks from cache, local data source (SQLite) or remote data source, whichever is
     * available first.
     * <p>
     * Note: {@link LoadLocationsCallback#onDataNotAvailable(Throwable)} ()} is fired if all data sources fail to
     * get the data.
     */
    @Override
    public void getLocations(@NonNull final LoadLocationsCallback callback) {

        // Respond immediately with cache if available and not dirty
        if (mCachedTasks != null && !mCacheIsDirty) {
            callback.onLocationsLoaded(new ArrayList<>(mCachedTasks.values()));
            return;
        }

        if (mCacheIsDirty) {
            // If the cache is dirty we need to fetch new data from the network.
            getLocatinosFromRemoteDataSource(callback);
        } else {
            // Query the local storage if available. If not, query the network.
            mLocationsLocalDataSource.getLocations(new LoadLocationsCallback() {
                @Override
                public void onLocationsLoaded(List<Location> tasks) {
                    refreshCache(tasks);
                    callback.onLocationsLoaded(new ArrayList<>(mCachedTasks.values()));
                }

                @Override
                public void onDataNotAvailable(Throwable t) {
                    getLocatinosFromRemoteDataSource(callback);
                }
            });
        }
    }

    @Override
    public void saveLocation(@NonNull Location task, SaveLocationCallback callback) {

        final boolean[] isSaveSuccess = {false};
        mLocationsRemoteDataSource.saveLocation(task, new SaveLocationCallback() {
            @Override
            public void onLocationSave() {
                isSaveSuccess[0] = true;
                mLocationsLocalDataSource.saveLocation(task, callback);
            }

            @Override
            public void onLocationSaveFailure(Throwable t) {
                isSaveSuccess[0] = false;
                callback.onLocationSaveFailure(t);
            }
        });


        // Do in memory cache update to keep the app UI up to date
        if (mCachedTasks == null) {
            mCachedTasks = new LinkedHashMap<>();
        }

        if (isSaveSuccess[0]) mCachedTasks.put(task.getId(), task);

    }


    @Override
    public void getLocation(@NonNull final Integer locationId, @NonNull final GetLocationCallback callback) {

        Location cachedTask = getLocationWithId(locationId);

        // Respond immediately with cache if available
        if (cachedTask != null) {
            callback.onLocationLoaded(cachedTask);
            return;
        }

        // Load from server/persisted if needed.

        // Is the task in the local data source? If not, query the network.
        mLocationsLocalDataSource.getLocation(locationId, new GetLocationCallback() {
            @Override
            public void onLocationLoaded(Location location) {
                // Do in memory cache update to keep the app UI up to date
                if (mCachedTasks == null) {
                    mCachedTasks = new LinkedHashMap<>();
                }
                mCachedTasks.put(location.getId(), location);
                callback.onLocationLoaded(location);
            }

            @Override
            public void onDataNotAvailable(Throwable t) {
                mLocationsRemoteDataSource.getLocation(locationId, new GetLocationCallback() {
                    @Override
                    public void onLocationLoaded(Location location) {
                        // Do in memory cache update to keep the app UI up to date
                        if (mCachedTasks == null) {
                            mCachedTasks = new LinkedHashMap<>();
                        }
                        mCachedTasks.put(location.getId(), location);
                        callback.onLocationLoaded(location);
                    }

                    @Override
                    public void onDataNotAvailable(Throwable t) {
                        callback.onDataNotAvailable(t);
                    }
                });
            }
        });
    }


    @Override
    public void deleteAllLocations() {
        mLocationsRemoteDataSource.deleteAllLocations();
        mLocationsLocalDataSource.deleteAllLocations();

        if (mCachedTasks == null) {
            mCachedTasks = new LinkedHashMap<>();
        }
        mCachedTasks.clear();
    }

    @Override
    public void deleteLocation(@NonNull Integer locationID) {
        mLocationsRemoteDataSource.deleteLocation(locationID);
        mLocationsLocalDataSource.deleteLocation(locationID);

        mCachedTasks.remove(locationID);
    }

    private void getLocatinosFromRemoteDataSource(@NonNull final LoadLocationsCallback callback) {
        mLocationsRemoteDataSource.getLocations(new LoadLocationsCallback() {
            @Override
            public void onLocationsLoaded(List<Location> locations) {
                refreshCache(locations);
                refreshLocalDataSource(locations);
                callback.onLocationsLoaded(new ArrayList<>(mCachedTasks.values()));
            }

            @Override
            public void onDataNotAvailable(Throwable t) {
                callback.onDataNotAvailable(t);
            }
        });
    }

    private void refreshCache(List<Location> tasks) {
        if (mCachedTasks == null) {
            mCachedTasks = new LinkedHashMap<>();
        }
        mCachedTasks.clear();
        for (Location task : tasks) {
            mCachedTasks.put(task.getId(), task);
        }
        mCacheIsDirty = false;
    }

    private void refreshLocalDataSource(List<Location> locations) {
        mLocationsLocalDataSource.deleteAllLocations();
        for (Location loc : locations) {
            mLocationsLocalDataSource.saveLocation(loc, new SaveLocationCallback() {
                @Override
                public void onLocationSave() {
                    Timber.tag("XXX Loc Res").d("onLocationSave");
                }

                @Override
                public void onLocationSaveFailure(Throwable t) {
                    Timber.d(t);
                }
            });
        }
    }

    @Nullable
    private Location getLocationWithId(@NonNull Integer id) {
        if (mCachedTasks == null || mCachedTasks.isEmpty()) {
            return null;
        } else {
            return mCachedTasks.get(id);
        }
    }
}
