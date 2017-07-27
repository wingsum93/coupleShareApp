package com.ericho.coupleshare.frag

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.view.View
import com.ericho.coupleshare.contant.LatLong
import com.ericho.coupleshare.interf.FabListener
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
class LocationShowFrag: SupportMapFragment(), OnMapReadyCallback, LocationsContract.View, FabListener {
    override var isActive: Boolean = false

    var mPresenter: LocationsContract.Presenter? = null

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        setHasOptionsMenu(true)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(LatLong.HK_C_LAT, LatLong.HK_C_LONG), 10f))
        googleMap.addMarker(MarkerOptions().position(LatLng(22.309111, 114.174993)).title("HK"))
        isActive = true
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    //my method

    override fun setPresenter(presenter: LocationsContract.Presenter) {
        this.mPresenter = presenter
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

    override fun onAttachFloatingActionListener(floatingActionButton: FloatingActionButton) {
        floatingActionButton.visibility = View.GONE
    }

    companion object {
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