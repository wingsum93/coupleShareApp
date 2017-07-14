package com.ericho.coupleshare.mvp

import android.content.Intent

/**
 * Created by steve_000 on 11/7/2017.
 * for project coupleShareApp
 * package name com.ericho.coupleshare.mvp
 */
interface PhotosAddContract{
    interface View : BaseView<Presenter> {
        fun showImageGallery()
        fun showCamera()

        fun showPhotoList(photos: List<Photo>)


        fun showSyncing(sync:Boolean)
    }

    interface Presenter : BasePresenter {
        fun loadPhotos()

        fun uploadPhoto()//start to sync image file

        fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)

        fun takePhoto()
        fun selectImageInGallery()

        var syncing :Boolean
    }
}