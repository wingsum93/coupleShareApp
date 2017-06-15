package com.ericho.coupleshare.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by steve_000 on 14/6/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.util
 */

public class GsonUtil {
    private static Gson gson;

    public Gson getDefault(){
        if(gson == null){
            gson = new GsonBuilder().create();
        }
        return gson;
    }
}
