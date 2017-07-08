package com.ericho.coupleshare.mvp.data

import com.ericho.coupleshare.mvp.Photo

/**
 * Created by steve_000 on 4/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.mvp.data
 */
class PhotoRepository
private constructor(dataSource:PhotoDataSource):PhotoDataSource{


    private val mRemoteDataSource:PhotoDataSource

    init {
        mRemoteDataSource = requireNotNull(dataSource)
    }
    override fun getPhotos(callback: PhotoDataSource.LoadPhotoCallback): List<Photo> {
        return mRemoteDataSource.getPhotos(callback)
    }

    override fun addPhoto(callback: PhotoDataSource.AddPhotoCallback) {
        return mRemoteDataSource.addPhoto(callback)
    }

    override fun deletePhoto(callback: PhotoDataSource.DeletePhotoCallback) {
        return mRemoteDataSource.deletePhoto(callback)
    }



    companion object {
        private var INSTANCE : PhotoRepository?=null

        fun getInstance(photoDataSource: PhotoDataSource?): PhotoRepository {
            if (INSTANCE == null) {
                INSTANCE = PhotoRepository( photoDataSource!!)
            }
            return INSTANCE as PhotoRepository
        }

        fun destroyInstance(){
            INSTANCE = null
        }
    }
}