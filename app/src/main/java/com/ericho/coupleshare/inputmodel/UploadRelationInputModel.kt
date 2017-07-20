package com.ericho.coupleshare.inputmodel

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by steve_000 on 18/7/2017.
 * for project coupleShareApp
 * package name com.ericho.coupleshare.inputmodel
 */
class UploadRelationInputModel :BaseAuthModel(),Serializable {

    @SerializedName("username")
    var username:String? = null;
    @SerializedName("message")
    var message:String? = null;
    @SerializedName("title")
    var title: String? = null;

}