package com.ericho.coupleshare.http.model

import com.google.gson.annotations.SerializedName

/**
 * Created by steve_000 on 7/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.http.model
 */
class BaseSingleResponse<T> {
    @SerializedName("status")
    var status: Boolean = false
    @SerializedName("extra")
    var extra: T? = null
    @SerializedName("errorMessage")
    var errorMessage: String? = null
    @SerializedName("otherMessage")
    var otherMessage: String? = null//use when success;
}