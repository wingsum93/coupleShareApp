package com.ericho.coupleshare.util

import android.location.Location
import android.net.Uri
import com.ericho.coupleshare.App
import com.ericho.coupleshare.model.LocationTo
import java.io.File
import java.io.InputStream
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by steve_000 on 14/7/2017.
 * for project coupleShareApp
 * package name com.ericho.coupleshare.util
 */

val Location.toLocation: com.ericho.coupleshare.mvp.Location
    get() {
        val x = com.ericho.coupleshare.mvp.Location()
        x.accurate = this.accuracy.toInt()
        x.date = Date()
        x.username = "eriii"
        x.attitude = this.altitude
        x.latitude = this.latitude
        x.longitude = this.longitude
        x.uploadBy = "eriii"
        return x
    }
val Location.toLocationTo: LocationTo
    get() {
        val x = LocationTo()
        x.accurate = this.accuracy.toInt()
        x.date = Date()
        x.username = "eriii"
        x.attitude = this.altitude
        x.latitude = this.latitude
        x.longitude = this.longitude
        x.uploadBy = "eriii"
        return x
    }
val <T> ArrayList<T>?.safe :ArrayList<T>
    get() {
        if (this == null)
            return ArrayList<T>()
        else
            return this
    }
fun List<Uri>.toFileList() :List<File>{
    return this.map { File(it.path) }
}
fun Uri.toOpenStream() :InputStream{
    return App.context!!.contentResolver.openInputStream(this)
}
val Int.float:Float
    get() {return this.toFloat()}