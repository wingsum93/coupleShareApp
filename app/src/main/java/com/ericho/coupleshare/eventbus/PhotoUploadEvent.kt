package com.ericho.coupleshare.eventbus

/**
 * Created by steve_000 on 25/7/2017.
 * for project coupleShareApp
 * package name com.ericho.coupleshare.eventbus
 */
class PhotoUploadEvent(val totalCount:Int,val currentCount:Int){
    val interrupt = false
    var photoName:String? = null

}