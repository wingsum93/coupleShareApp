package com.ericho.coupleshare.interf

/**
 * Created by steve_000 on 9/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.interf
 */
interface PermissionListener {
    fun onGranted() //已授权

    fun onDenied(deniedPermission: List<String>) //未授权
}





