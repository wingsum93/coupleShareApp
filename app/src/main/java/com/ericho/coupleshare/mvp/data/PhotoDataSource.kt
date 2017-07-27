package com.ericho.coupleshare.mvp.data

import com.ericho.coupleshare.mvp.PhotoBo

/**
 * Created by steve_000 on 4/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.mvp.data
 */
interface PhotoDataSource {

    interface LoadPhotoCallback{
        fun onLoadPhoto(photos:List<PhotoBo>?)
        fun onLoadPhotoFailure(e:Exception)
    }
    interface AddPhotoCallback{
        fun onAddPhoto()
        fun onAddPhotoFailure(e:Exception)
    }
    interface DeletePhotoCallback{
        fun onDelPhoto()
        fun onDelPhotoFailure(e:Exception)
    }

    fun getPhotos(callback: LoadPhotoCallback):List<PhotoBo>

    fun addPhoto(photos:List<PhotoBo>, callback: AddPhotoCallback)

    fun deletePhoto(callback: DeletePhotoCallback)


}