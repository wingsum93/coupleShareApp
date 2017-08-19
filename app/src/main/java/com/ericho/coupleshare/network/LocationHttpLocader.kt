package com.ericho.coupleshare.network

import android.content.Context
import android.os.Bundle
import android.os.Looper
import android.support.v4.content.AsyncTaskLoader
import com.ericho.coupleshare.App
import com.ericho.coupleshare.db.Dao
import com.ericho.coupleshare.network.model.BaseResponse
import com.ericho.coupleshare.model.LocationTo
import com.ericho.coupleshare.model.TimeOption
import com.ericho.coupleshare.mvp.Location
import com.ericho.coupleshare.util.NetworkUtil
import com.google.gson.reflect.TypeToken
import okhttp3.Call
import timber.log.Timber
import java.io.IOException

/**
 * Created by steve_000 on 29/7/2017.
 * for project coupleShareApp
 * package name com.ericho.coupleshare.http
 */
class LocationHttpLoader private constructor(
        context: Context,
        var timeOption: TimeOption,
        var includeLocalData: Boolean,
        var includeFriendData: Boolean) :
        AsyncTaskLoader<Result<LocationTo>>(context) {

  var H: Boolean = false
  var call: Call? = null

  override fun loadInBackground(): Result<LocationTo> {

    val list = Dao.getInstance().getLocationToList();
    Timber.d("in main thread? ${Looper.myLooper() == Looper.getMainLooper()}   this's H = $H ")
    Timber.d("timeOption =  $timeOption    includeLocalData = $includeLocalData  includeFriendData = $includeFriendData ")




    return Result.from(list)
  }

  override fun onStartLoading() {
    forceLoad()
  }

  override fun deliverResult(data: Result<LocationTo>?) {
    if(isReset) return

    super.deliverResult(data)
  }

  fun xxx() :Result<Location>{
    if (call != null) call!!.cancel()
    val req = NetworkUtil.getLocationList()
    call = NetworkUtil.execute(request = req)

    val response = call!!.execute()
    if(!response.isSuccessful) return Result.from(IOException("code: ${response.code()} ,Message= ${response.message()}"))

    val str = response.body().string()
    var ba : BaseResponse<Location> = App.gson.fromJson(str,object :TypeToken<BaseResponse<Location>>(){}.type)
    if(ba.status)
      return Result.Companion.from(ba.extra!!)
    else
      return Result.Companion.from(IOException(ba.errorMessage))
  }

  class BundleBuilder() {
    @JvmField
    var timeOption: TimeOption = TimeOption.ALL
    @JvmField
    var includeLocalData: Boolean = true
    @JvmField
    var includeFriendData: Boolean = false

    fun period(period: TimeOption): BundleBuilder {
      timeOption = period
      return this
    }

    fun includeLocal(boolean: Boolean): BundleBuilder {
      includeLocalData = boolean
      return this
    }

    fun includeFriend(boolean: Boolean): BundleBuilder {
      includeFriendData = boolean
      return this
    }

    fun build(): Bundle {
      val b = Bundle()
      b.putBoolean(F_INCLUDE_FRIEND, includeFriendData)
      b.putBoolean(F_INCLUDE_LOCAL, includeLocalData)
      b.putSerializable(F_LOCATION_PERIOD, timeOption)
      return b
    }
  }


  companion object {
    val F_INCLUDE_LOCAL = "A"
    val F_INCLUDE_FRIEND = "V"
    val F_LOCATION_PERIOD = "D"


    fun from(context: Context, bundle: Bundle?): LocationHttpLoader {
      val x = if (bundle != null) bundle else BundleBuilder().build()

      val includeLocal: Boolean = x.getBoolean(F_INCLUDE_LOCAL)
      val includeFriend: Boolean = x.getBoolean(F_INCLUDE_FRIEND)
      val timeOption: TimeOption = x.getSerializable(F_LOCATION_PERIOD) as TimeOption


      return LocationHttpLoader(
              context = context,
              includeFriendData = includeFriend,
              includeLocalData = includeLocal,
              timeOption = timeOption)
    }
  }
}