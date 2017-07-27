package com.ericho.coupleshare.mvp.presenter

import com.ericho.coupleshare.mvp.PhotoBo
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
//        mView.showPhotoList(dummyPhotoList())
    }

    override fun addNewPhotos() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun dummyPhotoList(): List<PhotoBo> {
        val res = ArrayList<PhotoBo>()
        val p = PhotoBo()
        p.suffixUrl = ""
        res.add(p)
        val p2 = PhotoBo()
        res.add(p2)
        return res
    }
}