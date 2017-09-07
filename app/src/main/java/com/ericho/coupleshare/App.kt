package com.ericho.coupleshare

import android.content.Context
import android.support.multidex.MultiDexApplication
import com.ericho.coupleshare.network.GlideApp
import com.facebook.stetho.Stetho
import com.google.gson.*
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import org.xutils.x
import timber.log.Timber
import java.util.*


/**
 * Created by steve_000 on 7/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare
 */
class App :MultiDexApplication() {

    var refWatcher:RefWatcher? = null

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        Stetho.initializeWithDefaults(this)// browser debug
        x.Ext.init(this)
        x.Ext.setDebug(BuildConfig.DEBUG)
        Timber.plant(Timber.DebugTree())
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        if(BuildConfig.DEBUG) {
            refWatcher = LeakCanary.install(this)
        }else{
            refWatcher = RefWatcher.DISABLED
        }
    }

    override fun onLowMemory() {
        GlideApp.with(this).onLowMemory();
        super.onLowMemory()
    }

    override fun onTrimMemory(level: Int) {
        GlideApp.with(this).onTrimMemory(level);
        super.onTrimMemory(level)
    }

    override fun onTerminate() {
        context = null
        super.onTerminate()
    }
    companion object {
        var context: Context? = null
        val gson: Gson by lazy {
            GsonBuilder()
                    .registerTypeAdapter( Date::class.java, JsonDeserializer<Date> { json, _, _ -> Date(json.asJsonPrimitive.asLong) })
                    .registerTypeAdapter( Date::class.java, JsonSerializer<Date> { date, _, _ ->
                        if (date == null){
                            return@JsonSerializer null
                        }else{
                            return@JsonSerializer JsonPrimitive(date.time)
                        }
                    })
                    .create()
        }

        fun getRefWatcher(context: Context): RefWatcher {
            val application = context.applicationContext as App
            return application.refWatcher!!
        }

    }
}