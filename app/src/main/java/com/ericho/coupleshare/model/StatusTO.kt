package com.ericho.coupleshare.model

import android.net.Uri
import org.xutils.db.annotation.Column
import org.xutils.db.annotation.Table

/**
 * Created by steve_000 on 14/7/2017.
 * for project coupleShareApp
 * package name com.ericho.coupleshare.model
 */
@Table(name = "statusTo")
class StatusTO {
    @Column(name = "id",isId = true,autoGen = false)
    var id:Int? = null
    @Column(name = "uri")
    var uri:Uri? = null
    @Column(name = "title")
    var title:String? = null
    @Column(name = "content")
    var content:String? = null
}