package com.ericho.coupleshare.mvp.presenter

import android.app.Activity
import android.content.Intent
import com.ericho.coupleshare.App
import com.ericho.coupleshare.Injection
import com.ericho.coupleshare.act.PhotoAddAct
import com.ericho.coupleshare.mvp.PhotosAddContract
import com.ericho.coupleshare.mvp.data.PhotoRepository

/**
 * Created by steve_000 on 11/7/2017.
 * for project coupleShareApp
 * package name com.ericho.coupleshare.mvp.presenter
 */
class AddPhotoPresenter (val view:PhotosAddContract.View):PhotosAddContract.Presenter {

    val respository:PhotoRepository

    init {
        respository = Injection.providePhotoRepository(App.context!!)
    }

    override fun start() {

    }

    override fun loadPhotos() {

        respository


    }

    override fun uploadPhoto() {

    }

    override fun takePhoto() {
        view.showCamera()
    }

    override fun selectImageInGallery() {
        view.showImageGallery()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

    }

    override var syncing: Boolean
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}


}