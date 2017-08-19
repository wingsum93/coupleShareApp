package com.ericho.coupleshare.frag


import android.os.Bundle
import android.support.v4.app.LoaderManager
import android.support.v4.content.Loader
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import com.ericho.coupleshare.R
import com.ericho.coupleshare.contant.LatLong
import com.ericho.coupleshare.network.LocationHttpLoader
import com.ericho.coupleshare.network.Result
import com.ericho.coupleshare.model.LocationTo
import com.ericho.coupleshare.model.TimeOption
import com.ericho.coupleshare.mvp.Location
import com.ericho.coupleshare.mvp.LocationsContract
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.jetbrains.anko.toast
import timber.log.Timber

/**
 * Created by steve_000 on 8/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.frag
 */
class LocationShowFrag : SupportMapFragment(), OnMapReadyCallback, LocationsContract.View {
  override var isActive: Boolean = false

  var mPresenter: LocationsContract.Presenter? = null

  var googleMap: GoogleMap? = null


  override fun onCreate(bundle: Bundle?) {
    super.onCreate(bundle)
    setHasOptionsMenu(true)
  }

  override fun onMapReady(googleMap: GoogleMap) {
    this.googleMap = googleMap
    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(LatLong.HK_C_LAT, LatLong.HK_C_LONG), 10f))
    loaderManager?.restartLoader(LOADER_ID, null, loaderCallback)
    Timber.d("loaderManager is $loaderManager")
  }

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

  }

  //my method

  override fun setPresenter(presenter: LocationsContract.Presenter) {
    this.mPresenter = presenter
  }

  override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
    super.onCreateOptionsMenu(menu, inflater)
    inflater!!.inflate(R.menu.refresh, menu)
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item!!.itemId) {
      R.id.menu_refresh -> {
        val b = LocationHttpLoader.BundleBuilder()

        b.includeFriendData = true
        b.timeOption = TimeOption.ONE_DAY

        loaderManager.restartLoader(LOADER_ID, b.build(), loaderCallback)
        return true
      }
      else -> return super.onOptionsItemSelected(item)
    }

  }

  override fun setLoadingIndicator(active: Boolean) {

  }

  override fun showLocations(locations: List<Location>) {
    Timber.d("loc $locations")
  }

  override fun showConfirmDeleteDialog(location: Location) {
    val ms = "lat:${location.latitude},long:${location.longitude}"
    val f = ConfirmDialog.newInstance(getString(R.string.confirm_to_delete),ms)
    f.setConfirmRunnable {
      mPresenter!!.deleteOneLocation(location)
    }
    f.show(fragmentManager,"confirm")
  }

  override fun showErrorMessage(error: String) {
    Timber.w(error)
    activity.toast(error)
  }

  override fun showNoLocations() {

  }

  override fun showFilteringPopUpMenu() {

  }

  override fun onDestroyView() {

    super.onDestroyView()
  }

  override fun onDestroy() {
    loaderManager.destroyLoader(LOADER_ID)
    super.onDestroy()
  }

  var loaderCallback: LoaderManager.LoaderCallbacks<Result<LocationTo>> = object : LoaderManager.LoaderCallbacks<Result<LocationTo>> {
    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Result<LocationTo>> {
      when (id) {
        LOADER_ID -> {
          val x = LocationHttpLoader.from(activity, args)
          Timber.d("onCreateLoader")
          x.startLoading()
          return x
        }
        else -> throw UnsupportedOperationException("")
      }

    }

    override fun onLoaderReset(loader: android.support.v4.content.Loader<Result<LocationTo>>?) {
      Timber.d("onLoaderReset")
      googleMap?.clear()
    }

    override fun onLoadFinished(loader: android.support.v4.content.Loader<Result<LocationTo>>?, result: Result<LocationTo>?) {
      Timber.d("onLoadFinished loader $loader .. data $result")
      if (result!!.success()) {
        val data = result.result
        data?.forEach {
          locationTo ->
          val ll = LatLng(locationTo.latitude!!, locationTo.longitude!!)
          googleMap?.addMarker(MarkerOptions()
                  .position(ll).title(locationTo.id.toString())
                  .snippet("accuracy= ${locationTo.accurate}"))
        }
      }

    }
  }


  companion object {
    val LOADER_ID = 155
    @JvmStatic
    fun newInstance(): LocationShowFrag {
      val frag = LocationShowFrag()
      val bundle = Bundle()
      frag.arguments = bundle
      frag.getMapAsync(frag)
      return frag
    }
  }
}