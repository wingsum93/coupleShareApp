package com.ericho.coupleshare.db

import com.ericho.coupleshare.model.LocationTo

/**
 * Created by steve_000 on 14/7/2017.
 * for project coupleShareApp
 * package name com.ericho.coupleshare.db
 */
interface CoupleShareDao {



    fun saveLocationTO(locations:List<LocationTo>)
    fun saveLocationTO(locations:LocationTo)
    fun delLocationTO(locations:LocationTo)

    fun getLocationToList():List<LocationTo>

}