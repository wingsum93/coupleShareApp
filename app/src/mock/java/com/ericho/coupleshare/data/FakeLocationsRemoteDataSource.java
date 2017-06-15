package com.ericho.coupleshare.data;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.ericho.coupleshare.mvp.Location;
import com.ericho.coupleshare.mvp.data.LocationDataSource;
import com.google.common.collect.Lists;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by steve_000 on 12/6/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.data
 */

public class FakeLocationsRemoteDataSource implements LocationDataSource {

    private static FakeLocationsRemoteDataSource INSTANCE;

    private static final Map<Integer, Location> LOCATIONS_SERVICE_DATA = new LinkedHashMap<>();

    // Prevent direct instantiation.
    private FakeLocationsRemoteDataSource() {
    }

    public static FakeLocationsRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FakeLocationsRemoteDataSource();
        }
        return INSTANCE;
    }


    @Override
    public void getLocations(@NonNull LoadLocationsCallback callback) {
        callback.onLocationsLoaded(Lists.newArrayList(LOCATIONS_SERVICE_DATA.values()));
    }

    @Override
    public void getLocation(@NonNull Integer locationId, @NonNull GetLocationCallback callback) {
        Location location = LOCATIONS_SERVICE_DATA.get(locationId);
        callback.onLocationLoaded(location);
    }

    @Override
    public void saveLocation(@NonNull Location location, SaveLocationCallback callback) {
        LOCATIONS_SERVICE_DATA.put(location.getId(), location);
        callback.onLocationSave();
    }

    @Override
    public void deleteAllLocations() {
        LOCATIONS_SERVICE_DATA.clear();
    }

    @Override
    public void deleteLocation(@NonNull Integer locationId) {
        LOCATIONS_SERVICE_DATA.remove(locationId);
    }

    @VisibleForTesting
    public void addLocations(Location... locations) {
        for (Location location : locations) {
            LOCATIONS_SERVICE_DATA.put(location.getId(), location);
        }
    }
}
