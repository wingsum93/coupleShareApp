package com.ericho.coupleshare.util

import android.content.Context
import com.ericho.coupleshare.contant.WebAddress
import okhttp3.*
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * Created by steve_000 on 9/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.util
 */
object NetworkUtil{

    private var okhttpClient: OkHttpClient? = null

    fun getOkhttpClient(): OkHttpClient {

        if (okhttpClient == null) {
            okhttpClient = OkHttpClient.Builder()
                    .readTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .build()
        }

        return okhttpClient as OkHttpClient
    }

    fun getXXXRequest(ss: String): Request {
        val builder = Request.Builder()
        builder.addHeader("XXX", "XXX")
        //        builder.post()
        return builder.build()
    }

    fun execute(okHttpClient: OkHttpClient, request: Request): Call {
        return okHttpClient.newCall(request)
    }

    fun getUrl(context: Context, suffix: String): String {

        val s = ServerAddressUtil.getServerAddress(context) + suffix
        return s
    }


    @Throws(IOException::class)
    fun getResponseByRequest(call: Call): Response {
        val response = call.execute()
        if (!response.isSuccessful) throw IOException(response.message())
        return response
    }

    @Throws(IOException::class)
    fun parseResponseToString(response: Response): String {
        if (!response.isSuccessful) throw IOException(response.message())
        return response.body().string()
    }

    //------------------------------------------------------------------------------

    @Throws(IOException::class)
    fun registerAccount(context: Context, username: String, password: String): Request {
        val builder = FormBody.Builder()
        builder.add("username", username)
        builder.add("password", password)

        val b = Request.Builder()
        b.post(builder.build())
        b.url(HttpUrl.parse(getUrl(context, WebAddress.API_REGISTER)))
        return b.build()
    }

    @Throws(IOException::class)
    fun loginAccount(context: Context, username: String, password: String): Request {
        val builder = FormBody.Builder()
        builder.add("username", username)
        builder.add("password", password)

        val b = Request.Builder()
        b.post(builder.build())
        b.url(HttpUrl.parse(getUrl(context, WebAddress.API_LOGIN)))
        return b.build()
    }
}