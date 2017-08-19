package com.ericho.coupleshare.act

import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.SparseArray
import com.ericho.coupleshare.interf.PermissionListener
import java.util.ArrayList
import java.util.Collections

/**
 * Created by steve_000 on 9/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.act
 */
open class BasePermissionActivity :AppCompatActivity() {

    val sp:SparseArray<PermissionListener> = SparseArray<PermissionListener>()


    fun checkSelfPermission( requestCode:Int, permissions:List<String>,  listener: PermissionListener){
        sp.append(requestCode,listener);
        val no_permission_list = permissions.filter {
            ContextCompat.checkSelfPermission(this,it)!= PackageManager.PERMISSION_GRANTED
        }
        if (no_permission_list.isNotEmpty()) {
            //check if we have no permission and not show dialog
            val show_setting_instead = no_permission_list.filter {
                !ActivityCompat.shouldShowRequestPermissionRationale(this,it)
            }.isEmpty()

            if(show_setting_instead){
                //show the setting page / dialog ??

            }else {
                ActivityCompat.requestPermissions(this, no_permission_list.toTypedArray(), requestCode)
            }
        }else{
            listener.onGranted();
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val permissionListener = sp.get(requestCode)
        when (requestCode) {
            requestCode -> if (grantResults.isNotEmpty()) {
                //存放没授权的权限
                val deniedPermissions = ArrayList<String>()
                for (i in grantResults.indices) {
                    val grantResult = grantResults[i]
                    val permission = permissions[i]
                    if (grantResult != PackageManager.PERMISSION_GRANTED) {
                        deniedPermissions.add(permission)
                    }
                }
                if (deniedPermissions.isEmpty()) {
                    //说明都授权了
                    permissionListener.onGranted()
                } else {
                    permissionListener.onDenied(deniedPermissions)
                }
            }
            else -> {
                permissionListener.onDenied(Collections.emptyList())
            }
        }
    }
}

private val  <E> Collection<E>.random: E
    get() {
        val i = this.size * Math.random().toInt()
        return elementAt(i)
    }

fun String.toList() :List<String>{
    val list = ArrayList<String>()
    list.add(this)
    return list
}