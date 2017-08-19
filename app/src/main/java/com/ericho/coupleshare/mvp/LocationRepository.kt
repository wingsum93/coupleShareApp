package com.ericho.coupleshare.mvp

import com.ericho.coupleshare.mvp.data.LocationDataSource
import timber.log.Timber
import java.util.ArrayList
import java.util.LinkedHashMap

/**
 * Created by steve_000 on 9/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.mvp
 */
class LocationsRepository// Prevent direct instantiation.
(val mLocationsRemoteDataSource: LocationDataSource,
                    private val mLocationsLocalDataSource: LocationDataSource) : LocationDataSource {

    /**
     * This variable has package local visibility so it can be accessed from tests.
     */
    internal var mCachedTasks: MutableMap<Int, Location>? = null

    /**
     * Marks the cache as invalid, to force an update the next time data is requested. This variable
     * has package local visibility so it can be accessed from tests.
     */
    internal var mCacheIsDirty = false

    /**
     * Gets tasks from cache, local data source (SQLite) or remote data source, whichever is
     * available first.
     *
     *
     * Note: [LoadLocationsCallback.onDataNotAvailable] ()} is fired if all data sources fail to
     * get the data.
     */
    override fun getLocations(callback: LocationDataSource.LoadLocationsCallback) {

        // Respond immediately with cache if available and not dirty
        if (mCachedTasks != null && !mCacheIsDirty) {
            callback.onLocationsLoaded(ArrayList(mCachedTasks!!.values))
            return
        }

        if (mCacheIsDirty) {
            // If the cache is dirty we need to fetch new data from the network.
            getLocatinosFromRemoteDataSource(callback)
        } else {
            // Query the local storage if available. If not, query the network.
            mLocationsLocalDataSource.getLocations(object : LocationDataSource.LoadLocationsCallback {
                override fun onLocationsLoaded(locations: List<Location>) {
                    refreshCache(locations)
                    callback.onLocationsLoaded(ArrayList(mCachedTasks!!.values))
                }

                override fun onDataNotAvailable(t: Throwable) {
                    getLocatinosFromRemoteDataSource(callback)
                }
            })
        }
    }

    override fun saveLocation(location: Location, callback: LocationDataSource.SaveLocationCallback) {

        val isSaveSuccess = booleanArrayOf(false)
        mLocationsRemoteDataSource.saveLocation(location, object : LocationDataSource.SaveLocationCallback {
            override fun onLocationSave() {
                isSaveSuccess[0] = true
                mLocationsLocalDataSource.saveLocation(location, callback)
            }

            override fun onLocationSaveFailure(t: Throwable) {
                isSaveSuccess[0] = false
                callback.onLocationSaveFailure(t)
            }
        })


        // Do in memory cache update to keep the app UI up to date
        if (mCachedTasks == null) {
            mCachedTasks = LinkedHashMap<Int, Location>()
        }

        if (isSaveSuccess[0]) mCachedTasks!!.put(location.id!!, location)

    }


    override fun getLocation(locationId: Int, callback: LocationDataSource.GetLocationCallback) {
        val cachedTask = getLocationWithId(locationId)

        // Respond immediately with cache if available
        if (cachedTask != null) {
            callback.onLocationLoaded(cachedTask)
            return
        }

        // Load from server/persisted if needed.

        // Is the task in the local data source? If not, query the network.
        mLocationsLocalDataSource.getLocation(locationId, object : LocationDataSource.GetLocationCallback {
            override fun onLocationLoaded(location: Location) {
                // Do in memory cache update to keep the app UI up to date
                if (mCachedTasks == null) {
                    mCachedTasks = LinkedHashMap<Int, Location>()
                }
                mCachedTasks!!.put(location.id!!, location)
                callback.onLocationLoaded(location)
            }

            override fun onDataNotAvailable(throwable: Throwable) {
                mLocationsRemoteDataSource.getLocation(locationId, object : LocationDataSource.GetLocationCallback {
                    override fun onLocationLoaded(location: Location) {
                        // Do in memory cache update to keep the app UI up to date
                        if (mCachedTasks == null) {
                            mCachedTasks = LinkedHashMap<Int, Location>()
                        }
                        mCachedTasks!!.put(location.id!!, location)
                        callback.onLocationLoaded(location)
                    }

                    override fun onDataNotAvailable(throwable: Throwable) {
                        callback.onDataNotAvailable(throwable)
                    }
                })
            }
        })
    }

    override fun deleteAllLocations() {
        mLocationsRemoteDataSource.deleteAllLocations()
        mLocationsLocalDataSource.deleteAllLocations()

        if (mCachedTasks == null) {
            mCachedTasks = LinkedHashMap<Int, Location>()
        }
        mCachedTasks!!.clear()
    }


    override fun deleteLocation(locationId: Int) {
        mLocationsRemoteDataSource.deleteLocation(locationId)
        mLocationsLocalDataSource.deleteLocation(locationId)

        mCachedTasks!!.remove(locationId)
    }

    private fun getLocatinosFromRemoteDataSource(callback: LocationDataSource.LoadLocationsCallback) {
        mLocationsRemoteDataSource.getLocations(object : LocationDataSource.LoadLocationsCallback {
            override fun onLocationsLoaded(locations: List<Location>) {
                refreshCache(locations)
                refreshLocalDataSource(locations)
                callback.onLocationsLoaded(ArrayList(mCachedTasks!!.values))
            }

            override fun onDataNotAvailable(t: Throwable) {
                callback.onDataNotAvailable(t)
            }
        })
    }

    private fun refreshCache(tasks: List<Location>) {
        if (mCachedTasks == null) {
            mCachedTasks = LinkedHashMap<Int, Location>()
        }
        mCachedTasks!!.clear()
        for (task in tasks) {
            mCachedTasks!!.put(task.id!!, task)
        }
        mCacheIsDirty = false
    }

    private fun refreshLocalDataSource(locations: List<Location>) {
        mLocationsLocalDataSource.deleteAllLocations()
        for (loc in locations) {
            mLocationsLocalDataSource.saveLocation(loc, object : LocationDataSource.SaveLocationCallback {
                override fun onLocationSave() {
                    Timber.tag("XXX Loc Res").d("onLocationSave")
                }

                override fun onLocationSaveFailure(t: Throwable) {
                    Timber.d(t)
                }
            })
        }
    }

    private fun getLocationWithId(id: Int): Location? {
        if (mCachedTasks == null || mCachedTasks!!.isEmpty()) {
            return null
        } else {
            return mCachedTasks!![id]
        }
    }

    companion object {

        private var INSTANCE: LocationsRepository? = null


        fun getInstance(tasksRemoteDataSource: LocationDataSource,
                        tasksLocalDataSource: LocationDataSource): LocationsRepository {
            if (INSTANCE == null) {
                INSTANCE = LocationsRepository(tasksRemoteDataSource, tasksLocalDataSource)
            }
            return INSTANCE as LocationsRepository
        }


        fun destroyInstance() {
            INSTANCE = null
        }
    }
}