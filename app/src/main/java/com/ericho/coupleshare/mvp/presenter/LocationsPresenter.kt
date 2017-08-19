package com.ericho.coupleshare.mvp.presenter

import com.ericho.coupleshare.App
import com.ericho.coupleshare.Injection
import com.ericho.coupleshare.mvp.Location
import com.ericho.coupleshare.mvp.LocationsContract
import com.ericho.coupleshare.mvp.LocationsRepository
import com.ericho.coupleshare.mvp.data.LocationDataSource

/**
 * Created by steve_000 on 8/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.mvp.presenter
 */
class LocationsPresenter constructor(
        val mView: LocationsContract.View,
        var locationsRepository: LocationsRepository) : LocationsContract.Presenter {
  override var filtering: LocationsFilterType = LocationsFilterType.All


  override fun start() {
    locationsRepository = Injection.provideLocationsRepository(App.context!!)
//        loadLocations(true)


  }

  override fun result(requestCode: Int, resultCode: Int) {

  }

  override fun loadLocations(forceUpdate: Boolean) {
    if (false) {

    } else {
      locationsRepository.getLocations(object : LocationDataSource.LoadLocationsCallback {
        override fun onLocationsLoaded(locations: List<Location>) {
          mView.showLocations(locations)
        }

        override fun onDataNotAvailable(t: Throwable) {
          mView.showErrorMessage(t.message.toString())
        }
      })
    }
  }

  override fun openLocationDetails(requestedLocation: Location) {

  }

  override fun deleteOneLocation(location: Location) {
    if (location.id != null) {
      locationsRepository.deleteLocation(location.id!!)
    }
  }

  override fun confirmDeleteOneLocation(location: Location) {
    mView.showConfirmDeleteDialog(location)
  }
}