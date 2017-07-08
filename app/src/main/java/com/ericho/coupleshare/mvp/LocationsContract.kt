package com.ericho.coupleshare.mvp

import com.ericho.coupleshare.mvp.presenter.LocationsFilterType

/**
 * Created by steve_000 on 8/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.mvp
 */
interface LocationsContract {

    interface View : BaseView<Presenter> {
        fun setLoadingIndicator(active: Boolean)

        fun showLocations(locations: List<Location>)


        fun showTaskMarkedActive()

        var isActive: Boolean

        fun showNoLocations()

        fun showFilteringPopUpMenu()

    }

    interface Presenter : BasePresenter {
        fun result(requestCode: Int, resultCode: Int)

        fun loadLocations(forceUpdate: Boolean)

        fun openLocationDetails(requestedLocation: Location)

        var filtering: LocationsFilterType
    }
}