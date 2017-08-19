package com.ericho.coupleshare.util

import android.os.Handler
import android.os.Looper
import com.ericho.coupleshare.network.model.BaseSingleResponse
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

/**
 * Created by steve_000 on 19/7/2017.
 * for project coupleShareApp
 * package name com.ericho.coupleshare.util
 */
class AHttpHelper<T :BaseSingleResponse<*> >(


        val failureMethod: ((Call,IOException) -> Unit),//run in main thread
        val successMethod: (Call,T) -> Unit,//run in main thread
        var transformMethod: ((String) -> T)  ///run in background thread
){

    val handler = Handler(Looper.getMainLooper())



    fun run(request: Request):Unit{

        val call = NetworkUtil.execute(request = request)

        call.enqueue(object :Callback{
            override fun onFailure(okCall: Call?, e: IOException?) {
                handler.post {
                    failureMethod.invoke(okCall!!,e!!)
                }
            }

            override fun onResponse(okhttpCall: Call?, response: Response?) {
                if(!response!!.isSuccessful){
                    onFailure(okhttpCall!!,IOException("${response.code()} + ${response.message()}"))
                    return
                }

                val str = response.body().string()//req background thread!!
                val res : T = transformMethod.invoke(str)

                if(!res.status){
                    onFailure(okhttpCall!!,IOException(res.errorMessage))
                    return
                }else{
                    //success
                    handler.post { successMethod.invoke(okhttpCall!!,res) }
                }
            }

        })
    }

    class Builder<T:BaseSingleResponse<*>>{
        var failureMethod: ((Call,IOException) -> Unit)? = null
        var successMethod: ((Call,T) -> Unit)? = null
        var transformMethod: ((String) -> T)? = null

        fun setFail(runnable:(Call, IOException) -> Unit): Builder<T> {
            failureMethod = runnable
            return this
        }
        fun setSuccessMethod(runnable:(Call, T) -> Unit): Builder<T> {
            successMethod = runnable
            return this
        }
        fun setTransformMethod(runnable:(string:String) -> T): Builder<T> {
            transformMethod = runnable
            return this
        }


        fun build(): AHttpHelper<T>{
            checkNotNull(failureMethod,{"fail method required"})
            checkNotNull(successMethod,{"success method required"})
            checkNotNull(transformMethod,{"transform method required"})
            return AHttpHelper<T>(
                    failureMethod = failureMethod!!,
                    successMethod = successMethod!!,
                    transformMethod = transformMethod!!)
        }
    }
}