package com.ericho.coupleshare.network

/**
 * a class for use in loader to capture if any exception
 * Created by steve_000 on 29/7/2017.
 * for project coupleShareApp
 * package name com.ericho.coupleshare.http
 */
class Result<T>  {
    var result:List<T>? = null
    var error:Exception? = null

    fun success():Boolean{
        return error==null
    }

    companion object {
      fun <T> from(c:Collection<T>):Result<T>{
        val r = Result<T>()
        r.result = ArrayList<T>(c)
        return r
      }
      fun <T> from(e:Exception):Result<T>{
        val r = Result<T>()
        r.error = e
        return r
      }
    }
}