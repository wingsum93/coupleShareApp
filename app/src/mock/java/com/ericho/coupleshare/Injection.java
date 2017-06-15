package com.ericho.coupleshare;

import android.content.Context;
import android.support.annotation.NonNull;

import com.ericho.coupleshare.data.FakeLocationsRemoteDataSource;
import com.ericho.coupleshare.data.FakeLoginRemoteDataSource;
import com.ericho.coupleshare.mvp.LocationsRepository;
import com.ericho.coupleshare.mvp.data.LoginRepository;
import com.ericho.coupleshare.mvp.data.local.LocationsLocalDataSource;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Created by steve_000 on 12/6/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare
 */

public class Injection {

    public static LoginRepository provideLoginRepository(Context context) {
        return LoginRepository.getInstance(FakeLoginRemoteDataSource.getInstance());
    }

    public static LocationsRepository provideLocationsRepository(@NonNull Context context) {
        checkNotNull(context);
        return LocationsRepository.getInstance(FakeLocationsRemoteDataSource.getInstance(),
                LocationsLocalDataSource.getInstance());
    }
}
