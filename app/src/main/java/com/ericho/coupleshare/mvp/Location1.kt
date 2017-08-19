package com.ericho.coupleshare.mvp

import com.ericho.coupleshare.model.LocationTo
import com.google.gson.annotations.SerializedName

import org.xutils.db.annotation.Column
import org.xutils.db.annotation.Table

import java.util.Date

/**
 * Created by steve_000 on 10/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.mvp
 */
@Table(name = "Location")
class Location {

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

  @SerializedName("uploaded")
  @Column(name = "uploaded")
  var uploaded:Boolean? = null

  fun LocationTo.toLocation():Location{
    val z = Location()
    z.id = -1
    z.accurate = this.accurate
    z.username = this.username
    z.uploadBy = this.uploadBy
    z.attitude = this.attitude
    z.latitude = this.latitude
    z.longitude = this.longitude
    z.date = this.date
    z.latitude = this.latitude
    return z
  }
}
