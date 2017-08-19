package com.ericho.coupleshare

import android.content.Context
import com.ericho.coupleshare.mvp.LocationsRepository
import com.ericho.coupleshare.mvp.data.FakePhotoDataSource
import com.ericho.coupleshare.mvp.data.LoginRepository
import com.ericho.coupleshare.mvp.data.PhotoRepository
import com.ericho.coupleshare.mvp.data.local.LocationsLocalDataSource
import com.ericho.coupleshare.mvp.data.remote.LocationRemoteDataSource
import com.ericho.coupleshare.mvp.data.remote.LoginRemoteDataSource

/**
 * Created by steve_000 on 5/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare
 */
object Injection {
  @JvmStatic
  fun provideLoginRepository(context: Context): LoginRepository {
    return LoginRepository.getInstance(LoginRemoteDataSource.getInstance(context))
  }

  @JvmStatic
  fun provideLocationsRepository(context: Context,
                                 locRemoteDataSource: LocationRemoteDataSource = LocationRemoteDataSource.getInstance(context),
                                 locLocalDataSource: LocationsLocalDataSource = LocationsLocalDataSource.getInstance()): LocationsRepository {
    return LocationsRepository.getInstance(
            locRemoteDataSource,
            locLocalDataSource)
  }

  @JvmStatic
  fun providePhotoRepository(context: Context): PhotoRepository {
    return PhotoRepository.getInstance(FakePhotoDataSource())
  }
}