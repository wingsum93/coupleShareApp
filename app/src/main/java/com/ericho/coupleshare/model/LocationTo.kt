package com.ericho.coupleshare.model

import com.google.gson.annotations.SerializedName
import org.xutils.db.annotation.Column
import org.xutils.db.annotation.Table
import java.util.*

/**
 * Created by steve_000 on 14/7/2017.
 * for project coupleShareApp
 * package name com.ericho.coupleshare.model
 */
@Table(name = "LocationTo")
class LocationTo {

    @SerializedName("id")
    @Column(name = "id", isId = true, autoGen = false)
    var id: Int? = null

    @SerializedName("username")
    @Column(name = "username")
    var username: String? = null

    @SerializedName("uploadBy")
    @Column(name = "uploadBy")
    var uploadBy: String? = null

    @SerializedName("attitude")
    @Column(name = "attitude")
    var attitude: Double? = null

    @SerializedName("latitude")
    @Column(name = "latitude")
    var latitude: Double? = null

    @SerializedName("longitude")
    @Column(name = "longitude")
    var longitude: Double? = null

    @SerializedName("accurate")
    @Column(name = "accurate")
    var accurate: Int? = null

    @SerializedName("collectDate")
    @Column(name = "date")
    var date: Date? = null
    @SerializedName("sync")
    @Column(name = "sync")
    var sync:Boolean = false
}