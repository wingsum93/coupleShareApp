package com.ericho.coupleshare.util

import android.os.Handler
import android.os.Looper
import com.ericho.coupleshare.mvp.Location
import com.ericho.coupleshare.mvp.Photo

/**
 * Created by steve_000 on 4/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.util
 */
object ThreadUtil {

    fun onMainThread(runnable: Runnable) {
        val mainHandler = Handler(Looper.getMainLooper())
        mainHandler.post(runnable)
    }
}

fun Photo.isRelevant(status:Location):Boolean{

    return false;
}
fun Photo.isSOS():Boolean{

    return false;
}
fun Any?.toString(): String {
    if (this == null) return "null"
    // 空检测之后，“this”会自动转换为非空类型，所以下面的 toString()
    // 解析为 Any 类的成员函数
    return toString()
}