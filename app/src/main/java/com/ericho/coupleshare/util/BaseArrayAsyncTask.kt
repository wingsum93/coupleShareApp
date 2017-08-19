package com.ericho.coupleshare.util

import android.os.AsyncTask

/**
 * Created by steve_000 on 25/7/2017.
 * for project coupleShareApp
 * package name com.ericho.coupleshare.util
 */
abstract class BaseArrayAsyncTask<Param> : AsyncTask<Param, String, Boolean>() {

    abstract var mFunction : (p:Param,index:Int)->Boolean
    override fun doInBackground(vararg params: Param): Boolean {

        if(params.isEmpty()) throw Exception("param required")
        //background
        val success_size = params.filterIndexed { index, param ->
            mFunction.invoke(param,index)
        }.size

        return success_size == params.size
    }



}