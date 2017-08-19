package com.ericho.coupleshare.network

import android.content.Context
import com.ericho.coupleshare.App
import com.ericho.coupleshare.contant.Key
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * Created by steve_000 on 9/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.http
 */
class UserInfoInterceptor : Interceptor {
    private val t = "UserInfoInterceptor"
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val header_username = App.context!!.getUserName()
        val header_token = App.context!!.getUserToken()

        val newRequest = request.newBuilder()
                .addHeader("username",header_username)
                .addHeader("token",header_token)
                .build()

        val response = chain.proceed(newRequest)


        return response
    }
}

private fun Context.getUserName(): String {
    val pref = this.getSharedPreferences(com.ericho.coupleshare.contant.Key.pref_name,0)
    return pref.getString(Key.loginName,"unknown")
}
private fun Context.getUserToken(): String {
    val pref = this.getSharedPreferences(com.ericho.coupleshare.contant.Key.pref_name,0)
    return pref.getString(Key.userToken,"999")
}
