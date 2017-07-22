package com.ericho.coupleshare.http

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import java.io.IOException

/**
 * Created by steve_000 on 9/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.http
 */
class LoggingInterceptor : Interceptor {
    private val t = "LoggingInterceptor"
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val t1 = System.nanoTime()


        val res = HashMap<String,String>()
        for(i  in 1 until request.headers().size()-1){
               res.put(request.headers().name(i),request.headers().value(i))
        }
        Timber.v("$res")

        Log.v(t, String.format("Sending request %s on %s%n%s",
                request.url(), chain.connection(), request.headers()))
        val response = chain.proceed(request)

        val t2 = System.nanoTime()
        val usedTime = (t2 - t1) / 1e6
        Log.v(t, String.format("Received response for %s in %.1fms%n%s",
                response.request().url(), usedTime, response.headers()))
        return response
    }
}