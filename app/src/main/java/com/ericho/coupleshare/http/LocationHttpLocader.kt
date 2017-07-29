package com.ericho.coupleshare.http

import android.content.Context
import android.os.Bundle
import android.os.Looper
import android.support.v4.content.AsyncTaskLoader
import com.ericho.coupleshare.db.Dao
import com.ericho.coupleshare.model.LocationTo
import timber.log.Timber
import java.io.Serializable

/**
 * Created by steve_000 on 29/7/2017.
 * for project coupleShareApp
 * package name com.ericho.coupleshare.http
 */
class LocationHttpLoader private constructor(
        context: Context,
        var locationPeriod:LocationPeriod,
        var includeLocalData:Boolean,
        var includeFriendData:Boolean):
        AsyncTaskLoader<List<LocationTo>>(context) {

    var H:Boolean = false


    override fun loadInBackground(): List<LocationTo> {

        val list = Dao.getInstance().getLocationToList();
        Timber.d("in main thread? ${Looper.myLooper() == Looper.getMainLooper()}   this's H = $H ")
        Timber.d("locationPeriod =  $locationPeriod    includeLocalData = $includeLocalData  includeFriendData = $includeFriendData ")
        return list
    }

    override fun onStartLoading() {
        forceLoad()
    }


    fun xxx(bundle: Bundle):LocationHttpLoader{

        return this
    }

    class BundleBuilder(){
        @JvmField
        var locationPeriod:LocationPeriod = LocationPeriod.ALL
        @JvmField
        var includeLocalData:Boolean = true
        @JvmField
        var includeFriendData:Boolean = false

        fun period(period: LocationPeriod):BundleBuilder{
            locationPeriod = period
            return this
        }

        fun includeLocal(boolean: Boolean):BundleBuilder{
            includeLocalData = boolean
            return this
        }

        fun includeFriend(boolean: Boolean):BundleBuilder{
            includeFriendData = boolean
            return this
        }

        fun build():Bundle{
            val b = Bundle()
            b.putBoolean(F_INCLUDE_FRIEND,includeFriendData)
            b.putBoolean(F_INCLUDE_LOCAL,includeLocalData)
            b.putSerializable(F_LOCATION_PERIOD,locationPeriod)
            return b
        }
    }

    enum class LocationPeriod:Serializable{
        ONE_DAY,
        SEVEN_DAY,
        TWO_WEEK,
        ONE_MONTH,
        ALL
    }

    companion object {
        val F_INCLUDE_LOCAL = "A"
        val F_INCLUDE_FRIEND = "V"
        val F_LOCATION_PERIOD = "D"


        fun from(context: Context,bundle: Bundle?):LocationHttpLoader{
            val x = if(bundle!=null) bundle else BundleBuilder().build()

            val includeLocal:Boolean = x.getBoolean(F_INCLUDE_LOCAL)
            val includeFriend:Boolean = x.getBoolean(F_INCLUDE_FRIEND)
            val locationPeriod:LocationPeriod = x.getSerializable(F_LOCATION_PERIOD) as LocationPeriod


            return LocationHttpLoader(
                    context= context,
                    includeFriendData = includeFriend,
                    includeLocalData = includeLocal,
                    locationPeriod = locationPeriod)
        }
    }
}