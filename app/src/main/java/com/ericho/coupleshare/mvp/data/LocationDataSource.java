package com.ericho.coupleshare.mvp.data;

import android.support.annotation.NonNull;

import com.ericho.coupleshare.mvp.Location;

import java.util.List;

/**
 * Created by steve_000 on 10/6/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.mvp
 */

public interface LocationDataSource {
    interface LoadLocationsCallback {

        void onLocationsLoaded(List<Location> locations);

        void onDataNotAvailable(Throwable t);
    }

    interface GetLocationCallback {

        void onLocationLoaded(Location location);

        void onDataNotAvailable(Throwable t);
    }
    interface SaveLocationCallback {

        void onLocationSave();

        void onLocationSaveFailure(Throwable t);
    }
    interface DeleteLocationCallback {

        void onLocationDeleteSuccess();

        void onLocationDeleteFailure(Throwable t);
    }

    void getLocations(@NonNull LoadLocationsCallback callback);

    void getLocation(@NonNull Integer locationId, @NonNull GetLocationCallback callback);

    void saveLocation(@NonNull Location location,SaveLocationCallback callback);


    void deleteAllLocations();

    void deleteLocation(@NonNull Integer locationId);
}
