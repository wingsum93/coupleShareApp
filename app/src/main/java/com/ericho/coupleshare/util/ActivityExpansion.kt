package com.ericho.coupleshare.util

import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.widget.Toast


/**
 * Created by steve_000 on 8/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.util
 */
fun AppCompatActivity.checkSelfPermission(  x:String):Int{
    val permissionCheck = ContextCompat.checkSelfPermission(this,
            x)
    return permissionCheck
}

fun Fragment.showToastMessage(  text:String){
    if(activity!=null) Toast.makeText(activity,text,Toast.LENGTH_SHORT).show()
}