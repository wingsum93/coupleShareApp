package com.ericho.coupleshare;

import android.content.Context;

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
    }


}
