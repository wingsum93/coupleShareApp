package com.ericho.coupleshare;

import android.content.Context;
import android.support.annotation.NonNull;

import com.ericho.coupleshare.mvp.LocationsRepository;
import com.ericho.coupleshare.mvp.data.LoginRepository;
import com.ericho.coupleshare.mvp.data.local.LocationsLocalDataSource;
import com.ericho.coupleshare.mvp.data.remote.LocationRemoteDataSource;
import com.ericho.coupleshare.mvp.data.remote.LoginRemoteDataSource;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Created by steve_000 on 12/6/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare
 */

public class Injection {

    public static LoginRepository provideLoginRepository(Context context) {
        return LoginRepository.getInstance(LoginRemoteDataSource.getInstance(context));
    }

    public static LocationsRepository provideLocationsRepository(@NonNull Context context) {
        checkNotNull(context);
        return LocationsRepository.getInstance(LocationRemoteDataSource.getInstance(context),
                LocationsLocalDataSource.getInstance());
    }
}
