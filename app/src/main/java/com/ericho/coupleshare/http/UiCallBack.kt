package com.ericho.coupleshare.http

import okhttp3.Call
import okhttp3.Response
import java.io.IOException

/**
 * Created by steve_000 on 8/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.http
 */
interface UiCallBack{
    fun onUiFailure(okCall: Call?, e: IOException?)
    fun onUiResponse(okhttpCall: Call?, response: Response?)
}