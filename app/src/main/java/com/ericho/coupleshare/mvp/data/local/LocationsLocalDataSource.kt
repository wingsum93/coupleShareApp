package com.ericho.coupleshare.mvp.data.local

import com.ericho.coupleshare.mvp.Location
import com.ericho.coupleshare.mvp.data.LocationDataSource
import com.ericho.coupleshare.util.NullUtil
import org.xutils.DbManager
import org.xutils.x
import timber.log.Timber
import java.io.IOException

/**
 * Created by steve_000 on 8/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.mvp.data.local
 */
class LocationsLocalDataSource: LocationDataSource {


    private val daoConfig: DbManager.DaoConfig

    private val dbManager: DbManager

    init {
        daoConfig = DbConfig.getDefaultConfig()
        dbManager = x.getDb(daoConfig)
    }


    fun destroyInstance() {
        if (INSTANCE != null) {
            try {
                INSTANCE?.dbManager!!.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        INSTANCE = null
    }

    override fun getLocations(callback: LocationDataSource.LoadLocationsCallback) {
        try {
            val locations = dbManager.findAll(Location::class.java)
            if (NullUtil.isNullOrEmpty(locations)) throw IOException("location no records")

            callback.onLocationsLoaded(locations)
        } catch (e: IOException) {
            callback.onDataNotAvailable(e)
        }

    }


    override fun saveLocation(location: Location, callback: LocationDataSource.SaveLocationCallback) {
        try {
            dbManager.save(location)
            callback.onLocationSave()
        } catch (e: IOException) {
            callback.onLocationSaveFailure(e)
        }

    }

    override fun deleteAllLocations() {
        try {
            dbManager.delete(Location::class.java)
        } catch (e: IOException) {
            Timber.w(e)
        }

    }


    override fun getLocation(locationId: Int, callback: LocationDataSource.GetLocationCallback) {
        try {
            val location = dbManager.findById(Location::class.java, locationId)
            if (NullUtil.isNull(location)) throw IOException("location not exists")

            callback.onLocationLoaded(location)
        } catch (e: IOException) {
            callback.onDataNotAvailable(e)
        }

    }

    override fun deleteLocation(locationId: Int) {
        try {
            dbManager.deleteById(Location::class.java, locationId)
        } catch (e: IOException) {
            Timber.w(e)
        }

    }


    companion object {
        @JvmField
        var INSTANCE : LocationsLocalDataSource? = null
        @JvmStatic
        fun getInstance() :LocationsLocalDataSource {
            if (INSTANCE == null) {
                INSTANCE = LocationsLocalDataSource()
            }

            return INSTANCE as LocationsLocalDataSource
        }
    }
}