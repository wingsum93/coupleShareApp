package com.ericho.coupleshare.http.model

import com.google.gson.annotations.SerializedName

/**
 * Created by steve_000 on 7/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.http
 */
class BaseResponse<T> {
    @SerializedName("status")
    private var status: Boolean = false
    @SerializedName("extra")
    private var extra: List<T>? = null

    @SerializedName("error_message")
    private var errorMessage: String? = null
    @SerializedName("other_message")
    private var otherMessage: String? = null//use when success;

}