package com.ericho.coupleshare.inputmodel

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Created by steve_000 on 4/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.inputmodel
 */
class UploadLocationInputModel:BaseAuthModel(){
    @SerializedName("locations") private var locations:List<Location>? = null

    internal class Location {
        private var username: String? = null
        private var latitude: Double? = null

        private var longitude: Double? = null
        private var accuracy: Double? = null
        private var date: Date? = null

    }
}