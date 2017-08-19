package com.ericho.coupleshare.mvp.data.remote

import android.content.Context
import com.ericho.coupleshare.App
import com.ericho.coupleshare.network.BaseUiCallback
import com.ericho.coupleshare.network.model.BaseResponse
import com.ericho.coupleshare.mvp.Location
import com.ericho.coupleshare.mvp.data.LocationDataSource
import com.ericho.coupleshare.util.NetworkUtil
import com.google.gson.reflect.TypeToken
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Response
import timber.log.Timber
import java.io.IOException

/**
 * Created by steve_000 on 8/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.mvp.data.remote
 */
open class LocationRemoteDataSource :LocationDataSource {



    private val client: OkHttpClient//can be ignore
    var call :Call? = null

    init {
        client = NetworkUtil.getOkhttpClient()
    }

    override fun getLocations(callback: LocationDataSource.LoadLocationsCallback) {
        val req = NetworkUtil.getLocationList()
        call = NetworkUtil.execute(request = req)
        call!!.enqueue(object :BaseUiCallback(){
            override fun onUiFailure(okCall: Call?, e: IOException?) {
                callback.onDataNotAvailable(e!!)
            }

            override fun onUiResponse(okhttpCall: Call?, response: Response?) {
                if(!response!!.isSuccessful){
                    return callback.onDataNotAvailable(IOException(response.message()))
                }
                val res = response.body().string()
                Timber.d(res)
                val type = object : TypeToken<BaseResponse<Location>>() {}.type
                val baseResponse = App.gson.fromJson<BaseResponse<Location>>(res, type)
                if (baseResponse.status) {
                    callback.onLocationsLoaded(baseResponse.extra!!)
                } else {
                    callback.onDataNotAvailable(IOException(baseResponse.errorMessage))
                }
            }
        })
    }


    override fun saveLocation(location: Location, callback: LocationDataSource.SaveLocationCallback) {
        val request = NetworkUtil.uploadLocation(location)
        val call = NetworkUtil.execute(client, request)
        call.enqueue(object :BaseUiCallback(){
            override fun onUiFailure(okCall: Call?, e: IOException?) {
                callback.onLocationSaveFailure(e!!)
            }

            override fun onUiResponse(okhttpCall: Call?, response: Response?) {
                if(!response!!.isSuccessful){
                    return callback.onLocationSaveFailure(IOException(response.message()))
                }
                val res = response.body().string()
                Timber.d(res)
                val type = object : TypeToken<BaseResponse<Location>>() {}.type
                val baseResponse = App.gson.fromJson<BaseResponse<Location>>(res, type)
                if (baseResponse.status) {
                    callback.onLocationSave()
                } else {
                    callback.onLocationSaveFailure(IOException(baseResponse.errorMessage))
                }
            }
        })
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
                INSTANCE = LocationRemoteDataSource()
            }
            return INSTANCE as LocationRemoteDataSource
        }
    }
}