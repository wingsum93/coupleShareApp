package com.ericho.coupleshare.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import org.parceler.Parcel
import org.xutils.db.annotation.Column
import org.xutils.db.annotation.Table
import java.io.Serializable
import java.util.Date

/**
 * Created by steve_000 on 14/7/2017.
 * for project coupleShareApp
 * package name com.ericho.coupleshare.model
 */
@Parcel
@Table(name = "LocationTo")
class LocationTo() : Serializable ,Parcelable{

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

    constructor(parcel: android.os.Parcel) : this() {
        id = parcel.readInt()
        username = parcel.readString()
        uploadBy = parcel.readString()
        attitude = parcel.readDouble()
        latitude = parcel.readDouble()
        longitude = parcel.readDouble()
        accurate = parcel.readInt()
        date = parcel.readSerializable() as Date
        sync = parcel.readByte() != 0.toByte()
    }

    override fun writeToParcel(dest: android.os.Parcel?, flags: Int) {
        dest!!.writeInt(id!!)
        dest!!.writeString(username!!)
        dest!!.writeString(uploadBy!!)
        dest!!.writeDouble(attitude!!)
        dest!!.writeDouble(latitude!!)
        dest!!.writeDouble(longitude!!)
        dest!!.writeInt(accurate!!)
        dest.writeSerializable(date)
        val tmp_sync:Byte = if (sync) 1 else 0
        dest.writeByte(tmp_sync)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<LocationTo> {
        override fun createFromParcel(parcel: android.os.Parcel): LocationTo {
            return LocationTo(parcel)
        }

        override fun newArray(size: Int): Array<LocationTo?> {
            return arrayOfNulls(size)
        }
    }
}