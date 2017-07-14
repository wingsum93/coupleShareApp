package com.ericho.coupleshare.inputmodel

import com.ericho.coupleshare.mvp.Location
import com.google.gson.annotations.SerializedName
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by steve_000 on 4/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.inputmodel
 */
class UploadLocationInputModel:BaseAuthModel(){
    @SerializedName("locations") var locations:ArrayList<Location> = ArrayList()



    companion object {
        val sample :UploadLocationInputModel
        get() {
            val x1 = UploadLocationInputModel()
            x1.locations .add(getRandomOne())
            return x1
        }
        fun getRandomOne():Location{
            val v = Location()
            v.username = "aa"
            return v
        }
    }
}