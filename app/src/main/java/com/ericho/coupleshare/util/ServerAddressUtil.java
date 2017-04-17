package com.ericho.coupleshare.util;

import android.content.Context;

import com.ericho.coupleshare.BuildConfig;
import com.ericho.coupleshare.contant.Key;

/**
 * Created by steve_000 on 16/4/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.util
 */

public class ServerAddressUtil {
    public static String getServerAddress(Context context){
        return context.getSharedPreferences("pref",Context.MODE_PRIVATE).getString(Key.server_address, BuildConfig.default_address);
    }
}
