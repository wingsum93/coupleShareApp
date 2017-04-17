package com.ericho.coupleshare.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by steve_000 on 17/4/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.http
 */

public class GsonUtil {
    private static Gson gson;

    public static Gson getGson() {
        if(gson == null){
            synchronized (GsonUtil.class){
                gson = new GsonBuilder()
                        .serializeNulls()
                        .create();
            }
        }
        return gson;
    }
}
