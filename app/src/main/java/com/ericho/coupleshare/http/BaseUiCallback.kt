package com.ericho.coupleshare.http

import android.os.Handler
import android.os.Looper
import android.os.Message
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException
import java.lang.ref.WeakReference

/**
 * Created by steve_000 on 8/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.http
 */
open abstract class BaseUiCallback: UiCallBack,Callback{

    //主线程Handler
    private val mHandler = MyHandler(Looper.getMainLooper(), this)

    override fun onFailure(call: Call?, e: IOException?) {
        val message = Message.obtain()
        message.what = REQ_ERROR
        message.obj = OkHttpErrorMSBO(call, e)
        mHandler.sendMessage(message)
    }

    override fun onResponse(call: Call?, response: Response?) {
        val message = Message.obtain()
        message.what = REQ_SUCCESS
        message.obj = OkHttpSuccessMSBO(call, response)
        mHandler.sendMessage(message)
    }



    private class MyHandler(looper: Looper, callback: UiCallBack) : Handler(looper) {
        private val okCallbackWeakReference: WeakReference<UiCallBack> = WeakReference(callback)

        override fun handleMessage(msg: Message) {
            when (msg.what) {
                REQ_SUCCESS -> {
                    val okCallback = okCallbackWeakReference.get()
                    if (okCallback != null) {
                        val a = msg.obj as OkHttpSuccessMSBO
                        okCallback.onUiResponse(a.call, a.response)
                    }
                }
                REQ_ERROR -> {
                    val okCallback2 = okCallbackWeakReference.get()
                    if (okCallback2 != null) {
                        val a = msg.obj as OkHttpErrorMSBO
                        okCallback2.onUiFailure(a.call, a.e)
                    }
                }
                else -> super.handleMessage(msg)
            }
        }
    }
    companion object {
        private val REQ_ERROR = 111
        private val REQ_SUCCESS = 333
    }


}
