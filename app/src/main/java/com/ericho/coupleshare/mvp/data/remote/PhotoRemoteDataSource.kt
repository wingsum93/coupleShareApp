package com.ericho.coupleshare.mvp.data.remote

import com.ericho.coupleshare.mvp.PhotoBo
import com.ericho.coupleshare.mvp.data.PhotoDataSource
import com.ericho.coupleshare.util.NetworkUtil

/**
 * Created by steve_000 on 11/7/2017.
 * for project coupleShareApp
 * package name com.ericho.coupleshare.mvp.data.remote
 */
class PhotoRemoteDataSource :PhotoDataSource {



    override fun getPhotos(callback: PhotoDataSource.LoadPhotoCallback): List<PhotoBo> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addPhoto(photos: List<PhotoBo>, callback: PhotoDataSource.AddPhotoCallback) {
        val call = NetworkUtil.getLocationList()

//        async(){
//
//        }
//        call.enqueue(object : BaseUiCallback(){
//            override fun onUiFailure(okCall: Call?, e: IOException?) {
//                callback.onDataNotAvailable(e!!)
//            }
//
//            override fun onUiResponse(okhttpCall: Call?, response: Response?) {
//                if(!response!!.isSuccessful){
//                    return callback.onDataNotAvailable(IOException(response.message()))
//                }
//                val res = response.body().string()
//                Timber.d(res)
//                val type = object : TypeToken<BaseResponse<Location>>() {}.type
//                val baseResponse = App.gson.fromJson<BaseResponse<Location>>(res, type)
//                if (baseResponse.status) {
//                    callback.onLocationsLoaded(baseResponse.extra!!)
//                } else {
//                    callback.onDataNotAvailable(IOException(baseResponse.errorMessage))
//                }
//            }
//        })
    }

    override fun deletePhoto(callback: PhotoDataSource.DeletePhotoCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}