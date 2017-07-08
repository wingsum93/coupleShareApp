package com.ericho.coupleshare.frag

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import com.ericho.coupleshare.R
import com.ericho.coupleshare.contant.LatLong
import com.ericho.coupleshare.interf.FabListener
import com.ericho.coupleshare.mvp.Location
import com.ericho.coupleshare.mvp.LocationsContract
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

/**
 * Created by steve_000 on 8/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.frag
 */
class LocationShowFrag: SupportMapFragment(), OnMapReadyCallback, LocationsContract.View, FabListener {


    lateinit var mPresenter: LocationsContract.Presenter

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        setHasOptionsMenu(true)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(LatLong.HK_C_LAT, LatLong.HK_C_LONG), 10f))
        googleMap.addMarker(MarkerOptions().position(LatLng(22.309111, 114.174993)).title("HK"))
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.change_server_address, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun setPresenter(presenter: LocationsContract.Presenter) {
        this.mPresenter = presenter
    }

    override fun setLoadingIndicator(active: Boolean) {

    }

    override fun showLocations(locations: List<Location>) {

    }

    override fun showTaskMarkedActive() {

    }

    override fun isActive(): Boolean {
        return activity != null
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