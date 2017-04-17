package com.ericho.coupleshare.http;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by steve_000 on 17/4/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.http
 */

public class Client {
    private static OkHttpClient client;


    public static OkHttpClient getClient(){
        if(client==null){
            synchronized (Client.class){
                client = new OkHttpClient.Builder()
                        .readTimeout(10, TimeUnit.SECONDS)
                        .writeTimeout(10,TimeUnit.SECONDS)
                        .retryOnConnectionFailure(false)
                        .build();
            }
        }
        return client;
    }
}
