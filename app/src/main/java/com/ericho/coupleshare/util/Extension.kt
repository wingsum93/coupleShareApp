package com.ericho.coupleshare.util

import android.content.Context
import android.location.Location
import com.ericho.coupleshare.App
import com.ericho.coupleshare.contant.Key
import com.ericho.coupleshare.model.LocationTo
import java.util.Date

/**
 * Created by steve_000 on 14/7/2017.
 * for project coupleShareApp
 * package name com.ericho.coupleshare.util
 */


val Location.toLocationTo: LocationTo
    get() {
        val username = App.context?.getUserName() ?: ""
        val x = LocationTo()
        x.accurate = this.accuracy.toInt()
        x.date = Date()
        x.username = username
        x.uploadBy = username
        x.attitude = this.altitude
        x.latitude = this.latitude
        x.longitude = this.longitude

        return x
    }

val Int.float:Float
    get() {return this.toFloat()}

fun String.toList() :List<String>{
    val list = java.util.ArrayList<String>()
    list.add(this)
    return list
}
fun Context.getUrl(urlSuffix:String) :String{
    return ServerAddressUtil.getServerAddress(this) + urlSuffix
}
fun <T> List<T>?.safe() :List<T> = if(this == null) ArrayList<T>()else this
fun Context.getUserName(): String {
    val pref = this.getSharedPreferences(com.ericho.coupleshare.contant.Key.pref_name,0)
    return pref.getString(Key.loginName,"unknown")
}

fun <T> List<T>.toArrayList():ArrayList<T>{
    return ArrayList(this)
}



