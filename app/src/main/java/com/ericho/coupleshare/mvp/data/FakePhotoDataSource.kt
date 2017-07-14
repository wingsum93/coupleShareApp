package com.ericho.coupleshare.mvp.data

import com.ericho.coupleshare.mvp.Photo
import com.ericho.coupleshare.mvp.isNull

/**
 * Created by steve_000 on 14/7/2017.
 * for project coupleShareApp
 * package name com.ericho.coupleshare.mvp.data
 */
class FakePhotoDataSource constructor() : PhotoDataSource {
    


    override fun getPhotos(callback: PhotoDataSource.LoadPhotoCallback): List<Photo> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addPhoto(photos: List<Photo>, callback: PhotoDataSource.AddPhotoCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deletePhoto(callback: PhotoDataSource.DeletePhotoCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        private var INSTANCE: FakePhotoDataSource? = null
        @JvmStatic
        fun getInstance() :FakePhotoDataSource{
            if(INSTANCE.isNull){
                INSTANCE = FakePhotoDataSource()
            }
            return INSTANCE as FakePhotoDataSource
        }
    }
}