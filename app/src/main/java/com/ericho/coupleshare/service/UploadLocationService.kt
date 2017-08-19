package com.ericho.coupleshare.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.ericho.coupleshare.App
import com.ericho.coupleshare.network.model.BaseSingleResponse
import com.ericho.coupleshare.util.HttpHelper
import com.google.gson.reflect.TypeToken
import timber.log.Timber

/**
 * Created by steve_000 on 29/7/2017.
 * for project coupleShareApp
 * package name com.ericho.coupleshare.service
 */
class UploadLocationService :Service(){

    var httpHelper: HttpHelper<BaseSingleResponse<Void>>? = null

    override fun onBind(intent: Intent?): IBinder {
        return Binder()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {


        httpHelper = HttpHelper.Builder<BaseSingleResponse<Void>>()
                .setTransformMethod {
                    App.gson.fromJson(it,object :TypeToken<BaseSingleResponse<Void>>(){}.type)
                }
                .setFail { call, ioException ->
                    Timber.w(ioException)
                }
                .setSuccessMethod { call, baseSingleResponse ->

                }
                .build()

        return START_NOT_STICKY
    }


    fun uploadLocation(){
        TODO()
//        val raw = Dao.getInstance().getLocationToList()
//        raw.filter {
//            it.sync.not()
//        }.map {
//            NetworkUtil.uploadLocation(it)
//        }.run {
//
//        }


    }
}