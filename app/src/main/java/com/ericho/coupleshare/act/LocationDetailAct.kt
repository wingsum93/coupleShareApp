package com.ericho.coupleshare.act

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.ericho.coupleshare.App
import com.ericho.coupleshare.R
import com.ericho.coupleshare.adapter.LocationDetailAdapter
import com.ericho.coupleshare.contant.LatLong
import com.ericho.coupleshare.model.LocationTo
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import timber.log.Timber

/**
 * Created by steve_000 on 3/8/2017.
 * for project coupleShareApp
 * package name com.ericho.coupleshare.act
 */
class LocationDetailAct:AppCompatActivity(), OnMapReadyCallback {

    var mapView:MapView? = null
    var googleMap:GoogleMap? = null
    var recyclerView:RecyclerView? = null
    var adapter: LocationDetailAdapter? = null
    var mLayoutManager:RecyclerView.LayoutManager? = null
    var mItem:ArrayList<LocationTo> = ArrayList<LocationTo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_location_detail)
        mapView = findViewById(R.id.mapView)
        mapView?.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
        val i = intent
//        val b  = i.getParcelableArrayListExtra<LatLng>("")
//        val b  = i.getParcelableExtra("")
        val items:ArrayList<LocationTo> = i.getParcelableArrayListExtra("example")
        Timber.d(App.gson.toJson(items))
        recyclerView = findViewById(R.id.recyclerView)


        mLayoutManager = GridLayoutManager(this,2)
        adapter = LocationDetailAdapter(this,mItem)
        recyclerView!!.layoutManager = mLayoutManager
        recyclerView!!.adapter = adapter
    }


    override fun onMapReady(googleMap: GoogleMap?) {
        this.googleMap = googleMap
        googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(LatLong.HK_C_LAT, LatLong.HK_C_LONG), 10f))
    }

    override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }

    override fun onStop() {
        mapView?.onStop()
        super.onStop()
    }

    override fun onPause() {
        mapView?.onPause()
        super.onPause()
    }

    override fun onResume() {
        mapView?.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        mapView?.onDestroy()
        super.onDestroy()
    }
    override fun onSaveInstanceState(outState: Bundle?) {
        mapView?.onSaveInstanceState(outState)
        super.onSaveInstanceState(outState)
    }

    override fun onLowMemory() {
        mapView?.onLowMemory()
        super.onLowMemory()
    }
}