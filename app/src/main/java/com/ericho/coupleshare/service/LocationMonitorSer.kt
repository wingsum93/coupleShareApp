package com.ericho.coupleshare.service

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Binder
import android.os.IBinder
import com.ericho.coupleshare.App
import com.ericho.coupleshare.constant.BroadcastConstant
import com.ericho.coupleshare.db.Dao
import com.ericho.coupleshare.model.LocationTo
import com.ericho.coupleshare.util.toLocationTo
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import timber.log.Timber


/**
 * Created by steve_000 on 11/7/2017.
 * for project coupleShareApp
 * package name com.ericho.coupleshare.service
 */
class LocationMonitorSer : Service(){
    val mFusedLocationClient by lazy { LocationServices.getFusedLocationProviderClient(this); }
    val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            for (location in locationResult!!.locations) {
                // Update UI with location data
                // ...
                EventBus.getDefault().post(location.toLocationTo)
            }
        }
    }
    var mRequestingLocationUpdates = false
    var broadcastReceiver : LocationRequestReceiver? = null
    var loadingLastKnownLocation = false

    override fun onBind(intent: Intent?): IBinder {
        return Binder()
    }

    override fun onCreate() {
        super.onCreate()
        EventBus.getDefault().register(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        Timber.d("service running!!")
        if (!mRequestingLocationUpdates) {
            val mLocationRequest = LocationRequest()
            mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            mLocationRequest.fastestInterval = 5 *60 * 1000
            mLocationRequest.interval = 10 *60 * 1000
            mLocationRequest.maxWaitTime = (30* 60 * 1000)
            mLocationRequest.smallestDisplacement = 50f
            startLocationUpdates(mLocationRequest);
        }

        val filter = IntentFilter(BroadcastConstant.REQ_LOC_MONITOR)
        broadcastReceiver = LocationRequestReceiver()
        registerReceiver(broadcastReceiver,filter)

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        stopLocationUpdates()
        unregisterReceiver(broadcastReceiver)
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

    private fun startLocationUpdates(mLocationRequest:LocationRequest) {

        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                mLocationCallback,
                null /* Looper */)
        mRequestingLocationUpdates = true
    }

    private fun stopLocationUpdates() {
        mRequestingLocationUpdates = false
        mFusedLocationClient.removeLocationUpdates(mLocationCallback)
    }
    inner class LocationRequestReceiver:BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent!!.action) {
                BroadcastConstant.REQ_LOC_MONITOR -> {
                    getLastKnownLocation()
                }
                else -> {
                    //skip
                }
            }
        }
    }

    private fun getLastKnownLocation() {
        if (loadingLastKnownLocation) {
            return
        }

        mFusedLocationClient.lastLocation
                .addOnSuccessListener {
                    location ->
                    if (location!=null) {
                        EventBus.getDefault().post(location.toLocationTo)
                    }
                }.addOnCompleteListener {
                    loadingLastKnownLocation = false
                }.addOnFailureListener {
                    Timber.w(it)
                }
        loadingLastKnownLocation = true
    }


    @Subscribe
    fun onLocationEvent(event:LocationTo){
        Timber.d("onLocationEvent "+ App.gson.toJson(event))
        Dao.getInstance().saveLocationTO(event)
    }
}


