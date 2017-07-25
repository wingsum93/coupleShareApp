package com.ericho.coupleshare.service

import android.app.Service
import android.content.Intent
import android.os.AsyncTask
import android.os.Binder
import android.os.IBinder
import com.ericho.coupleshare.App
import com.ericho.coupleshare.eventbus.PhotoUploadEvent
import com.ericho.coupleshare.http.model.BaseSingleResponse
import com.ericho.coupleshare.util.BaseArrayAsyncTask
import com.ericho.coupleshare.util.AHttpHelper
import com.ericho.coupleshare.util.NetworkUtil
import com.google.gson.reflect.TypeToken
import okhttp3.Response
import org.greenrobot.eventbus.EventBus
import timber.log.Timber
import java.io.File
import java.io.IOException
import java.util.Timer

/**
 * Created by steve_000 on 24/7/2017.
 * for project coupleShareApp
 * package name com.ericho.coupleshare.service
 */
class UploadPhotoService :Service() {
    val mBinder by lazy { MyBinder() }
    val mTimer :Timer = Timer()


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {



        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder {
        return mBinder
    }

    inner class MyBinder : Binder() {
        val service: UploadPhotoService
            get() = this@UploadPhotoService
    }

    override fun onDestroy() {
        mTimer.cancel()
        super.onDestroy()
    }

    fun uploadImage( files:List<File>){
        val task = UploadPhotoAsyncTask()
//        task.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR,files)
//        task.execute(files)
        task.fileList = files
        task.execute()
    }

    private class UploadPhotoAsyncTask : AsyncTask<File, String, Boolean>(){

        var fileList:List<File>? = null

        override fun onPreExecute() {
            EventBus.getDefault().post(PhotoUploadEvent(0,0))
        }
        override fun doInBackground(vararg params: File): Boolean {

            if(fileList!!.isEmpty()) throw Exception("param required")
            //background
            val success_size = fileList!!.filterIndexed { index, param ->
                mFunction.invoke(param,fileList!!.size,index)
            }.size

            return success_size == fileList!!.size
        }

        var mFunction: (file:File,totalCount:Int,index:Int) -> Boolean

        init{
            mFunction = {
                f: File, totalCount:Int,index: Int ->
                val req = NetworkUtil.photo_upload(file = f,tags = null)
                val call = NetworkUtil.execute(request = req)

                try {
                    val response = call.execute()
                    if (!response.isSuccessful) throw IOException("${response.code()}  ${response.message()}")

                    val str = response.body().string()

                    val r :BaseSingleResponse<String> = App.gson.fromJson(str,object :TypeToken<BaseSingleResponse<String>>(){}.type)

                    if(r.status.not()) false


                    EventBus.getDefault().post(PhotoUploadEvent(totalCount,index))
                     true
                }catch (e:IOException){
                    Timber.w(e)
                    false
                }
            }

        }

    }
}
