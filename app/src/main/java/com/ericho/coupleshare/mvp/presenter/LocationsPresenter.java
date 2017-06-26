package com.ericho.coupleshare.mvp.presenter;

import android.support.annotation.NonNull;

import com.ericho.coupleshare.mvp.Location;
import com.ericho.coupleshare.mvp.LocationsContract;
import com.ericho.coupleshare.mvp.LocationsRepository;

/**
 * Created by steve_000 on 12/6/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.mvp.presenter
 */

public class LocationsPresenter implements LocationsContract.Presenter {

    private LocationsRepository locationsRepository;

    @Override
    public void start() {

    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void loadLocations(boolean forceUpdate) {

    }

    @Override
    public void openLocationDetails(@NonNull Location requestedLocation) {

    }

    @Override
    public LocationsFilterType getFiltering() {
        return null;
    }

    @Override
    public void setFiltering(LocationsFilterType filterType) {

    }
}
