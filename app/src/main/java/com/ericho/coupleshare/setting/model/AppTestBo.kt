package com.ericho.coupleshare.setting.model

import java.io.Serializable

/**
 * Created by steve_000 on 8/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.setting.model
 */
class AppTestBo : Serializable {

    var code: String? = null
    var name: String? = null
    var displayName: String? = null


    constructor()
    constructor(code:String?, name:String?, displayName:String?){
        this.code = code
        this.name = name
        this.displayName = displayName
    }

    companion object {
        fun create(code: String, name: String, displayName: String): AppTestBo {
            val appTestBo = AppTestBo()
            appTestBo.code = (code)
            appTestBo.name = (name)
            appTestBo.displayName = (displayName)
            return appTestBo
        }
    }
}