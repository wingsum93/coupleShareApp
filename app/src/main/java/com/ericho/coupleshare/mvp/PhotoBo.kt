package com.ericho.coupleshare.mvp

import com.ericho.coupleshare.App
import com.ericho.coupleshare.util.NetworkUtil
import com.google.gson.annotations.SerializedName
import java.util.Date

/**
 * Created by steve_000 on 4/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.mvp
 */
class PhotoBo {
    @SerializedName("id")
    var id :Int? = null
    @SerializedName("photoName")
    var photoName:String? = null
    @SerializedName("uploadDate")
    var uploadDate:Date? = null
    @SerializedName("username")
    var username:String = ""
    @SerializedName("suffixUrl")
    var suffixUrl:String = ""

    val fullPath:String get() {
        return NetworkUtil.getUrl(App.context!!,suffix = suffixUrl)
    }

    companion object {

        fun sampleSequence():Sequence<PhotoBo>{
            val p = PhotoBo()
            p.suffixUrl = "api/viewPhoto/48fdd11d-7afb-42a5-96fd-ff7d3031e97e?username=eric"
            return generateSequence(p,{p})
        }
    }
}