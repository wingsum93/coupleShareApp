package com.ericho.coupleshare.mvp.data.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.ericho.coupleshare.mvp.Location;
import com.ericho.coupleshare.mvp.data.LocationDataSource;
import com.ericho.coupleshare.util.NullUtil;

import org.xutils.DbManager;
import org.xutils.x;

import java.io.IOException;
import java.util.List;

import timber.log.Timber;

/**
 * Created by steve_000 on 10/6/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.mvp
 */

public class LocationsLocalDataSource implements LocationDataSource {

    private static LocationsLocalDataSource INSTANCE ;

    private DbManager.DaoConfig daoConfig;

    private DbManager dbManager;

    private LocationsLocalDataSource() {
        daoConfig = DbConfig.getDefaultConfig();
        dbManager = getDbManager();
    }

    public static LocationsLocalDataSource getInstance(){
        if(INSTANCE == null){
            INSTANCE = new LocationsLocalDataSource();
        }

        return INSTANCE;
    }

    public void destroyInstance(){
        if(INSTANCE == null){
            try {
                INSTANCE.dbManager.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        INSTANCE = null;
    }

    private DbManager getDbManager(){
        return x.getDb(daoConfig);
    }


    @Override
    public void getLocations(@NonNull LoadLocationsCallback callback) {
        try {
            List<Location> locations = dbManager.findAll(Location.class);
            if(NullUtil.isNullOrEmpty(locations)) throw new IOException("location no records");

            callback.onLocationsLoaded(locations);
        }catch (IOException e){
            callback.onDataNotAvailable(e);
        }
    }

    @Override
    public void getLocation(@NonNull Integer locationId, @NonNull GetLocationCallback callback) {
        try {
            Location location = dbManager.findById(Location.class,locationId);
            if(NullUtil.isNull(location)) throw new IOException("location not exists");

            callback.onLocationLoaded(location);
        }catch (IOException e){
            callback.onDataNotAvailable(e);
        }
    }

    @Override
    public void saveLocation(@NonNull Location location,SaveLocationCallback callback) {
        try {
            dbManager.save(location);
            callback.onLocationSave();
        }catch (IOException e){
            callback.onLocationSaveFailure(e);
        }
    }

    @Override
    public void deleteAllLocations() {
        try {
            dbManager.delete(Location.class);
        }catch (IOException e){
            Timber.w(e);
        }
    }

    @Override
    public void deleteLocation(@NonNull Integer locationId) {
        try {
            dbManager.deleteById(Location.class,locationId);
        }catch (IOException e){
            Timber.w(e);
        }
    }
}
