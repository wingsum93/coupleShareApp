package com.ericho.coupleshare

import android.content.Context
import com.ericho.coupleshare.data.FakeLocationsRemoteDataSource
import com.ericho.coupleshare.data.FakeLoginRemoteDataSource
import com.ericho.coupleshare.mvp.LocationsRepository
import com.ericho.coupleshare.mvp.data.LoginRepository
import com.ericho.coupleshare.mvp.data.local.LocationsLocalDataSource

/**
 * Created by steve_000 on 5/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare
 */
object Injection {

    @JvmStatic
    fun provideLoginRepository(context: Context):LoginRepository {
        return LoginRepository.getInstance(FakeLoginRemoteDataSource.getInstance())
    }
    @JvmStatic
    fun provideLocationsRepository(context: Context): LocationsRepository {
        return LocationsRepository.getInstance(FakeLocationsRemoteDataSource.getInstance(),
                LocationsLocalDataSource.getInstance())
    }
}