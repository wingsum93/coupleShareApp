package com.ericho.coupleshare.util

import com.ericho.coupleshare.network.model.BaseResponse
import okhttp3.Call
import okhttp3.Request
import java.io.IOException

/**
 * Created by steve_000 on 19/7/2017.
 * for project coupleShareApp
 * package name com.ericho.coupleshare.util
 */
class HttpHelperB<T : BaseResponse<*>>(
        
        val failureMethod: ((Call,IOException) -> Unit),//run in main thread
        val successMethod: (Call,T) -> Unit,//run in main thread
        var transformMethod: ((String) -> T)  ///run in background thread
){

    fun run(request: Request):Unit{

        val call = NetworkUtil.execute(request = request)


        val response = call.execute()
        if(!response.isSuccessful){
            val e = IOException("${response.code()} + ${response.message()}")
            failureMethod.invoke(call!!,e!!)
            return
        }
        val str = response.body().string()//req background thread!!
        val res : T = transformMethod.invoke(str)

        if(!res.status){
            val e = IOException(res.errorMessage)
            failureMethod.invoke(call!!,e!!)
            return
        }else{
            //success
            successMethod.invoke(call!!,res)
        }
    }

    class Builder<T:BaseResponse<*>>{
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


        fun build(): HttpHelperB<T>{
            checkNotNull(failureMethod,{"fail method required"})
            checkNotNull(successMethod,{"success method required"})
            checkNotNull(transformMethod,{"transform method required"})
            return HttpHelperB<T>(
                    failureMethod = failureMethod!!,
                    successMethod = successMethod!!,
                    transformMethod = transformMethod!!)
        }
    }
}