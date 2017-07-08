package com.ericho.coupleshare.mvp.data.remote

import android.content.Context
import android.os.Handler
import android.os.HandlerThread
import com.ericho.coupleshare.mvp.Location
import com.ericho.coupleshare.mvp.data.LocationDataSource
import com.ericho.coupleshare.util.NetworkUtil
import okhttp3.OkHttpClient

/**
 * Created by steve_000 on 8/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.mvp.data.remote
 */
class LocationRemoteDataSource constructor(val context: Context) :LocationDataSource {


    private val mHandlerThread: HandlerThread

    private val mHandler: Handler

    private val client: OkHttpClient


    init {
        mHandlerThread = HandlerThread("loc-remote")
        mHandler = Handler(mHandlerThread.looper)
        client = NetworkUtil.getOkhttpClient()
    }

    override fun getLocations(callback: LocationDataSource.LoadLocationsCallback) {

    }


    override fun saveLocation(location: Location, callback: LocationDataSource.SaveLocationCallback) {

    }

    override fun deleteAllLocations() {

    }

    override fun getLocation(locationId: Int, callback: LocationDataSource.GetLocationCallback) {

    }

    override fun deleteLocation(locationId: Int) {

    }

    companion object {
        @JvmField
        var INSTANCE : LocationRemoteDataSource? = null
        @JvmStatic
        fun getInstance(context: Context) :LocationRemoteDataSource{
            if(INSTANCE==null){
                INSTANCE = LocationRemoteDataSource(context)
            }
            return INSTANCE as LocationRemoteDataSource
        }
    }
}