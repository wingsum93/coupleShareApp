package com.ericho.coupleshare.mvp.presenter

import com.ericho.coupleshare.R
import com.ericho.coupleshare.mvp.Photo
import com.ericho.coupleshare.mvp.PhotosContract
import java.util.ArrayList

/**
 * Created by steve_000 on 4/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.mvp.presenter
 */
class PhotoPresenter( view:PhotosContract.View) :PhotosContract.Presenter{


    private val mView:PhotosContract.View

    init{
        mView = requireNotNull(view)
    }

    override fun start() {

    }

    override fun loadPhotos() {
        mView.showPhotoList(dummyPhotoList())
    }

    override fun addNewPhotos() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun dummyPhotoList(): List<Photo> {
        val res = ArrayList<Photo>()
        val p = Photo()
        p.imageInt = R.drawable.ic_menu_manage
        res.add(p)
        val p2 = Photo()
        p.imageInt = R.drawable.ic_menu_camera
        res.add(p2)
        return res
    }
}