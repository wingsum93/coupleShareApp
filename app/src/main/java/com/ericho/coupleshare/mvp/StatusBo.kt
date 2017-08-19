package com.ericho.coupleshare.mvp

import com.ericho.coupleshare.App
import com.ericho.coupleshare.util.NetworkUtil

/**
 * Use for display the status of user and their friend
 *
 * Created by steve_000 on 4/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.mvp
 */
open class StatusBo{
    var id:Int? = null;
    var photoUrlSuffix:String? = null;
    var title:String? = null;
    var content:String? = null;

    val fullPath:String get() {
        return NetworkUtil.getUrl(App.context!!,suffix = photoUrlSuffix!!)
    }
    companion object {
        fun template():ArrayList<StatusBo>{
            val list = ArrayList<StatusBo>()

            list.add(StatusBo.fromDrawableName("fire_orange_64.png"))
            list.add(StatusBo.fromDrawableName("fire_orange_64.png"))
            list.add(StatusBo.fromDrawableName("fire_orange_64.png"))
            return list
        }
        fun fromDrawableName(fileName:String):StatusBo{
            val x = StatusBo()
            x.title = "Weather good"
            x.content = "Today 's weather "
//            val uri = Uri.parse("android.resource://com.ericho.coupleshare/drawable/"+fileName)
            x.photoUrlSuffix = "api/viewPhoto/48fdd11d-7afb-42a5-96fd-ff7d3031e97e?username=eric"
            return x
        }
    }
}