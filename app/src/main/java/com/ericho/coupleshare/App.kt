package com.ericho.coupleshare

import android.app.Application
import android.content.Context
import butterknife.ButterKnife
import com.facebook.stetho.Stetho
import org.xutils.x
import timber.log.Timber

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
        ButterKnife.setDebug(BuildConfig.DEBUG)
        Timber.plant(Timber.DebugTree())
    }

    override fun onTerminate() {
        context = null
        super.onTerminate()
    }
    companion object {
        var context: Context? = null

    }
}