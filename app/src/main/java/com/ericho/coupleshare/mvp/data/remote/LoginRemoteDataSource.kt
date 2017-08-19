package com.ericho.coupleshare.mvp.data.remote

import android.content.Context
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import com.ericho.coupleshare.App
import com.ericho.coupleshare.network.model.BaseSingleResponse
import com.ericho.coupleshare.mvp.data.LoginDataSource
import com.ericho.coupleshare.util.NetworkUtil
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import timber.log.Timber
import java.io.IOException
import java.net.SocketTimeoutException

/**
 * Created by steve_000 on 8/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.mvp.data.remote
 */
class LoginRemoteDataSource private constructor(var context: Context):LoginDataSource {


    private val mHandlerThread: HandlerThread

    private val mHandler: Handler
    private val mMainHandler: Handler

    private val client: OkHttpClient

    init {
        mHandlerThread = HandlerThread("user-remote")
        mHandlerThread.start()
        mHandler = Handler(mHandlerThread.getLooper())
        mMainHandler = Handler(Looper.getMainLooper())
        client = NetworkUtil.getOkhttpClient()
    }

    override fun login(username: String, password: String, callback: LoginDataSource.LoginCallback) {
        mHandler.post {
            try {
                val request = NetworkUtil.loginAccount(context, username, password)
                val call = NetworkUtil.execute(client, request)
                val response = call.execute()
                val res = NetworkUtil.parseResponseToString(response)
                Timber.d(res)
                val type = object : TypeToken<BaseSingleResponse<String>>() {}.type
                val baseSingleResponse = App.gson.fromJson<BaseSingleResponse<String>>(res, type)
                if (baseSingleResponse.status) {
                    callback.onLoginSuccess()
                } else {
                    callback.onLoginFailure(IOException(baseSingleResponse.errorMessage))
                }
            } catch (e: SocketTimeoutException) {
                callback.onLoginFailure(e)
            } catch (e: IOException) {
                e.printStackTrace()
                callback.onLoginFailure(e)
            }
        }
    }

    override fun register(username: String, password: String, callback: LoginDataSource.RegisterCallback) {
        mHandler.post {
            try {
                val request = NetworkUtil.registerAccount(context, username, password)
                val call = NetworkUtil.execute(client, request)
                val response = call.execute()
                val res = NetworkUtil.parseResponseToString(response)

                val type = object : TypeToken<BaseSingleResponse<String>>() {}.type
                val baseSingleResponse = App.gson.fromJson<BaseSingleResponse<String>>(res, type)
                if (baseSingleResponse.status) {
                    callback.onRegisterSuccess()
                } else {
                    callback.onRegisterFailure(IOException(baseSingleResponse.errorMessage))
                }
            } catch (e: IOException) {
                e.printStackTrace()
                callback.onRegisterFailure(e)
            }
        }
    }

    companion object {
        private var INSTANCE : LoginRemoteDataSource? = null

        @JvmStatic
        fun getInstance(context: Context):LoginRemoteDataSource{
            if(INSTANCE==null){
                INSTANCE = LoginRemoteDataSource(context)
            }
            return INSTANCE as LoginRemoteDataSource
        }
    }
}