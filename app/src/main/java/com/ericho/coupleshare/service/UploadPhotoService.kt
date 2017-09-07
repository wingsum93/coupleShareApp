package com.ericho.coupleshare.service

import android.app.Service
import android.content.Intent
import android.os.AsyncTask
import android.os.Binder
import android.os.IBinder
import com.ericho.coupleshare.App
import com.ericho.coupleshare.eventbus.PhotoUploadEvent
import com.ericho.coupleshare.network.model.BaseSingleResponse
import com.ericho.coupleshare.util.NetworkUtil
import com.google.gson.reflect.TypeToken
import org.greenrobot.eventbus.EventBus
import timber.log.Timber
import java.io.File
import java.io.IOException
import java.util.*

/**
 * Created by steve_000 on 24/7/2017.
 * for project coupleShareApp
 * package name com.ericho.coupleshare.service
 */
class UploadPhotoService :Service() {
    val mBinder by lazy { MyBinder() }
    val mTimer :Timer = Timer()


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {



        return START_NOT_STICKY
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
        task.fileList = files
        task.execute()
    }

    private class UploadPhotoAsyncTask : AsyncTask<File, String, Boolean>(){

        var  fileList:List<File>? = null

        override fun onPreExecute() {
            EventBus.getDefault().post(PhotoUploadEvent(0,0))
        }
        override fun doInBackground(vararg params: File): Boolean {

            if(fileList!!.isEmpty()) throw Exception("param required")
            val totalCount = fileList!!.size
            //background
            for ((index, value) in fileList!!.withIndex()){
                val b = mFunction.invoke(value)
                EventBus.getDefault().post(PhotoUploadEvent(totalCount,index+1))
            }
            return true
        }

        var mFunction: (file:File) -> Boolean

        init{
            mFunction = {
                f: File ->
                val req = NetworkUtil.photo_upload(file = f,tags = null)
                val call = NetworkUtil.execute(request = req)

                try {
                    val response = call.execute()
                    if (!response.isSuccessful) throw IOException("${response.code()}  ${response.message()}")

                    val str = response.body().string()

                    val r :BaseSingleResponse<String> = App.gson.fromJson(str,object :TypeToken<BaseSingleResponse<String>>(){}.type)

                    if(r.status.not())
                        throw IOException(r.errorMessage)

                     true
                }catch (e:IOException){
                    Timber.w(e)
                    false
                }
            }

        }

        override fun onPostExecute(result: Boolean?) {
            super.onPostExecute(result)
            Timber.v("onPostExecute ${result}")
        }

        override fun onCancelled() {
            super.onCancelled()
            Timber.v("onCancelled")
        }
    }
}
