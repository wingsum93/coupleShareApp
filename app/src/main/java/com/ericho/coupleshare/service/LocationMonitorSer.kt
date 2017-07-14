package com.ericho.coupleshare.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.ericho.coupleshare.App
import com.ericho.coupleshare.Injection
import com.ericho.coupleshare.db.Dao
import com.ericho.coupleshare.model.LocationTo
import com.ericho.coupleshare.mvp.LocationsRepository
import com.ericho.coupleshare.mvp.data.LocationDataSource
import com.ericho.coupleshare.util.toLocation
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
                EventBus.getDefault().post(location.toLocation)
            }
        }
    }
    var mRequestingLocationUpdates = false


    override fun onBind(intent: Intent?): IBinder {
        return Binder()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {


        if (mRequestingLocationUpdates) {
            val mLocationRequest = LocationRequest()
            mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            mLocationRequest.interval = 10 * 1000
            mLocationRequest.maxWaitTime = (30*1000)
            mLocationRequest.fastestInterval = 5 * 1000
            startLocationUpdates(mLocationRequest);
        }


        return START_STICKY
    }

    override fun onDestroy() {
        stopLocationUpdates()
        super.onDestroy()
    }

    private fun startLocationUpdates(mLocationRequest:LocationRequest) {

        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                mLocationCallback,
                null /* Looper */)
        mRequestingLocationUpdates = true
    }

    private fun stopLocationUpdates() {
        mFusedLocationClient.removeLocationUpdates(mLocationCallback)
    }



    @Subscribe
    fun onLocationEvent(event:LocationTo){
        Timber.d("onLocationEvent "+ App.gson.toJson(event))
        Dao.getInstance().saveLocationTO(event)
    }
}


