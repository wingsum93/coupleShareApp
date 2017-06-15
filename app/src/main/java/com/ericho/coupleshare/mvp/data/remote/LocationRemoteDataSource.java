package com.ericho.coupleshare.mvp.data.remote;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;

import com.ericho.coupleshare.mvp.Location;
import com.ericho.coupleshare.mvp.data.LocationDataSource;
import com.ericho.coupleshare.util.NetworkUtil;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by steve_000 on 14/6/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.mvp.data.remote
 */

public class LocationRemoteDataSource implements LocationDataSource {

    private static LocationRemoteDataSource INSTANCE ;

    private HandlerThread mHandlerThread;

    private Handler mHandler;

    private OkHttpClient client;

    private Context context;

    private LocationRemoteDataSource(Context context){
        this.context = context;
        mHandlerThread = new HandlerThread("loc-remote");
        mHandler = new Handler(mHandlerThread.getLooper());
        client = NetworkUtil.getOkhttpClient();
    }

    public static LocationRemoteDataSource getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = new LocationRemoteDataSource(context);
        }
        return INSTANCE;
    }


    @Override
    public void getLocations(@NonNull LoadLocationsCallback callback) {

    }

    @Override
    public void getLocation(@NonNull Integer locationId, @NonNull GetLocationCallback callback) {

    }

    @Override
    public void saveLocation(@NonNull Location location, SaveLocationCallback callback) {

    }

    @Override
    public void deleteAllLocations() {

    }

    @Override
    public void deleteLocation(@NonNull Integer locationId) {

    }
}
