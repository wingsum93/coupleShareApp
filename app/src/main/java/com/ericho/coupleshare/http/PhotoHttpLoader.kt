package com.ericho.coupleshare.http


import android.content.Context
import android.os.Bundle
import android.support.annotation.WorkerThread
import android.support.v4.content.AsyncTaskLoader
import com.ericho.coupleshare.App
import com.ericho.coupleshare.http.model.BaseResponse
import com.ericho.coupleshare.mvp.PhotoBo
import com.ericho.coupleshare.util.NetworkUtil
import com.google.gson.reflect.TypeToken
import java.io.IOException

/**
 * Created by steve_000 on 29/7/2017.
 * for project coupleShareApp
 * package name com.ericho.coupleshare.http
 */
class PhotoHttpLoader private constructor(
        context: Context,
        val includeFriendPhoto:Boolean):
        AsyncTaskLoader<WrapperObject<PhotoBo>>(context) {

    @WorkerThread
    override fun loadInBackground(): WrapperObject<PhotoBo> {

        val wo = WrapperObject<PhotoBo>()

        val request = NetworkUtil.photo_get()
        val call = NetworkUtil.execute(request = request)
        val response = call.execute()
        if(!response!!.isSuccessful){
            wo.error =  IOException("${response.code()} + ${response.message()}")
            return wo
        }
        val str = response.body().string()//req background thread!!
        val baseResponse:BaseResponse<PhotoBo> = App.gson.fromJson(str,object : TypeToken<BaseResponse<PhotoBo>>(){}.type)

        if(!baseResponse.status){
            wo.error = IOException(baseResponse.errorMessage)
            return wo
        }else{
            //success
            wo.result = baseResponse.extra!!
        }

        return wo
    }

    override fun onStartLoading() {
        forceLoad()
    }


    class BundleBuilder(){
        @JvmField
        var includeFriendData:Boolean = false


        fun includeFriend(boolean: Boolean):BundleBuilder{
            includeFriendData = boolean
            return this
        }

        fun build(): Bundle {
            val b = Bundle()
            b.putBoolean(F_INCLUDE_FRIEND,includeFriendData)
            return b
        }
    }

    companion object {
        val F_INCLUDE_LOCAL = "A"
        val F_INCLUDE_FRIEND = "V"
        val F_LOCATION_PERIOD = "D"
        fun from(context: Context,bundle: Bundle?):PhotoHttpLoader{
            val x = if(bundle!=null) bundle else PhotoHttpLoader.BundleBuilder().build()

            val includeFriend:Boolean = x.getBoolean(LocationHttpLoader.F_INCLUDE_FRIEND)

            return PhotoHttpLoader(
                    context= context,
                    includeFriendPhoto = includeFriend)
        }
    }

}