package com.ericho.coupleshare.network.model

import com.google.gson.annotations.SerializedName

/**
 * Created by steve_000 on 7/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.http
 */
class BaseResponse<T> {
    @SerializedName("status")
    var status: Boolean = false
    @SerializedName("extra")
    var extra: List<T>? = null

    @SerializedName("error_message")
    var errorMessage: String? = null
    @SerializedName("other_message")
    var otherMessage: String? = null//use when success;

}