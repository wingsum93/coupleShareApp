package com.ericho.coupleshare;

import android.content.Context;

import com.facebook.stetho.Stetho;

import org.xutils.x;

import timber.log.Timber;

/**
 * Created by steve_000 on 15/4/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare
 */

public class Application extends android.app.Application {
    private static Context context;
    public static Context getContext(){
        return context;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        Stetho.initializeWithDefaults(this);// browser debug
        x.Ext.init(this);
        Timber.plant(new Timber.DebugTree());
    }

    @Override
    public void onTerminate() {
        context = null;
        super.onTerminate();
    }
}
