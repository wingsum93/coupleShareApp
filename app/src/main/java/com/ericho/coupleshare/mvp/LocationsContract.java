package com.ericho.coupleshare.mvp;

import android.support.annotation.NonNull;

import com.ericho.coupleshare.mvp.presenter.LocationsFilterType;

import java.util.List;

/**
 * Created by steve_000 on 12/6/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.mvp
 */

public interface LocationsContract {

    interface View extends BaseView<Presenter> {
        void setLoadingIndicator(boolean active);

        void showLocations(List<Location> locations);


        void showTaskMarkedActive();

        boolean isActive();

        void showNoLocations();

        void showFilteringPopUpMenu();

    }

    interface Presenter extends BasePresenter {
        void result(int requestCode, int resultCode);

        void loadLocations(boolean forceUpdate);

        void openLocationDetails(@NonNull Location requestedLocation);

        LocationsFilterType getFiltering();

        void setFiltering(LocationsFilterType filterType);
    }


}
