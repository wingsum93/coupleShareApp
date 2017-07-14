package com.ericho.coupleshare

import android.app.Application
import android.content.Context
import com.facebook.stetho.Stetho
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import org.xutils.x
import timber.log.Timber
import java.util.*


/**
 * Created by steve_000 on 7/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare
 */
class App :Application() {


    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        Stetho.initializeWithDefaults(this)// browser debug
        x.Ext.init(this)
        x.Ext.setDebug(BuildConfig.DEBUG)
        Timber.plant(Timber.DebugTree())
    }

    override fun onTerminate() {
        context = null
        super.onTerminate()
    }
    companion object {
        var context: Context? = null
        val  gson: Gson by lazy {
            GsonBuilder()
                    .registerTypeAdapter( Date::class.java, JsonDeserializer<Date> { json, _, _ -> Date(json.asJsonPrimitive.asLong) })
                    .create()
        }

    }
}