package com.ericho.coupleshare.mvp.presenter

import com.ericho.coupleshare.mvp.Location
import com.ericho.coupleshare.mvp.LocationsContract
import com.ericho.coupleshare.mvp.LocationsRepository

/**
 * Created by steve_000 on 8/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.mvp.presenter
 */
class LocationsPresenter: LocationsContract.Presenter {
    override var filtering: LocationsFilterType = LocationsFilterType.All

    private val locationsRepository: LocationsRepository? = null

    override fun start() {

    }

    override fun result(requestCode: Int, resultCode: Int) {

    }

    override fun loadLocations(forceUpdate: Boolean) {

    }

    override fun openLocationDetails(requestedLocation: Location) {

    }


}