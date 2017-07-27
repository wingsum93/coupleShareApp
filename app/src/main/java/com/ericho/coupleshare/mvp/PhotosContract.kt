package com.ericho.coupleshare.mvp

/**
 * Created by steve_000 on 6/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.mvp
 */

interface PhotosContract{
    interface View : BaseView<Presenter> {
        fun showAddPhotoUI()

        fun showPhotoList(photos: List<PhotoBo>)

    }

    interface Presenter : BasePresenter {
        fun loadPhotos()

        fun addNewPhotos()
    }
}