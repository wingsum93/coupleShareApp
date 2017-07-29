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
import com.ericho.coupleshare.http.LocationHttpLoader
import com.ericho.coupleshare.model.LocationTo
import com.ericho.coupleshare.mvp.Location
import com.ericho.coupleshare.mvp.LocationsContract
import com.ericho.coupleshare.util.showToastMessage
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import timber.log.Timber

/**
 * Created by steve_000 on 8/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.frag
 */
class LocationShowFrag: SupportMapFragment(), OnMapReadyCallback, LocationsContract.View {
    override var isActive: Boolean = false

    var mPresenter: LocationsContract.Presenter? = null

    var googleMap:GoogleMap? = null


    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        setHasOptionsMenu(true)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(LatLong.HK_C_LAT, LatLong.HK_C_LONG), 10f))
        loaderManager?.restartLoader(LOADER_ID,null,loaderCallback)
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
        inflater!!.inflate(R.menu.clear,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            R.id.clear ->{
                val b = LocationHttpLoader.BundleBuilder()

                b.includeFriendData = true
                b.locationPeriod = LocationHttpLoader.LocationPeriod.ONE_DAY

                loaderManager.restartLoader(LOADER_ID,b.build(),loaderCallback)
                return true
            }
            else ->return super.onOptionsItemSelected(item)
        }

    }

    override fun setLoadingIndicator(active: Boolean) {

    }

    override fun showLocations(locations: List<Location>) {
        Timber.d("loc $locations")
    }

    override fun showTaskMarkedActive() {

    }

    override fun showErrorMessage(error: String) {
        Timber.w(error)
        showToastMessage(error)
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

    var loaderCallback   :LoaderManager.LoaderCallbacks<List<LocationTo>> = object :LoaderManager.LoaderCallbacks<List<LocationTo>>{
        override fun onCreateLoader(id: Int, args: Bundle?): Loader<List<LocationTo>> {
            when(id){
                LOADER_ID -> {
                    val x = LocationHttpLoader.from(activity,args)
                    Timber.d("onCreateLoader")
                    x.startLoading()
                    return x
                }else ->throw UnsupportedOperationException("")
            }

        }

        override fun onLoaderReset(loader: android.support.v4.content.Loader<List<LocationTo>>?) {
            Timber.d("onLoaderReset")
            googleMap?.clear()
        }

        override fun onLoadFinished(loader: android.support.v4.content.Loader<List<LocationTo>>?, data: List<LocationTo>?) {
            Timber.d("onLoadFinished loader $loader .. data $data")
            data?.forEach {
                locationTo ->
                val ll = LatLng(locationTo.latitude!!,locationTo.longitude!!)
                googleMap?.addMarker(MarkerOptions()
                        .position(ll).title(locationTo.id.toString())
                        .snippet("accuracy= ${locationTo.accurate}"))
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