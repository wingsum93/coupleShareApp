package com.ericho.coupleshare.http

/**
 * a class for use in loader to capture if any exception
 * Created by steve_000 on 29/7/2017.
 * for project coupleShareApp
 * package name com.ericho.coupleshare.http
 */
class WrapperObject<T>  {
    var result:List<T>? = null
    var error:Exception? = null

    fun success():Boolean{
        return error==null
    }
}