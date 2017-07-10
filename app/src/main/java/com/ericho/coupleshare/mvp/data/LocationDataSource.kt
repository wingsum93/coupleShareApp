package com.ericho.coupleshare.mvp.data

import com.ericho.coupleshare.mvp.Location

/**
 * Created by steve_000 on 7/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.mvp.data
 */
interface LocationDataSource {
    interface LoadLocationsCallback {

        fun onLocationsLoaded(locations: List<Location>)

        fun onDataNotAvailable(t: Throwable)
    }

    interface GetLocationCallback {

        fun onLocationLoaded(location: Location)

        fun onDataNotAvailable(throwable: Throwable)
    }

    interface SaveLocationCallback {

        fun onLocationSave()

        fun onLocationSaveFailure(t: Throwable)
    }

    interface DeleteLocationCallback {

        fun onLocationDeleteSuccess()

        fun onLocationDeleteFailure(t: Throwable)
    }

    fun getLocations(callback: LoadLocationsCallback)

    fun getLocation(locationId: Int, callback: GetLocationCallback)

    fun saveLocation(location: Location, callback: SaveLocationCallback)


    fun deleteAllLocations()

    fun deleteLocation(locationId: Int)
}