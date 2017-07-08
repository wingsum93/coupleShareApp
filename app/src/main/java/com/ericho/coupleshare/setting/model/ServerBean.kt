package com.ericho.coupleshare.setting.model

import android.support.annotation.DrawableRes

/**
 * Created by steve_000 on 8/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.setting.model
 */
class ServerBean {

    var url: String? = null//
    var displayName: String? = null//
    @DrawableRes
    var resourceInteger: Int = 0

    constructor(){}
    constructor(displayName:String,url:String,@DrawableRes resourceInteger:Int){
        this.displayName = displayName
        this.url = url
        this.resourceInteger = resourceInteger
    }
}