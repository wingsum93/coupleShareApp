package com.ericho.coupleshare.frag

import android.app.Activity
import android.support.v4.app.Fragment
import com.ericho.coupleshare.App


/**
 * Created by steve_000 on 8/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.frag
 */
open class BaseFrag:Fragment() {


    override fun onDestroy() {
        super.onDestroy()
//        val refWatcher = App.getRefWatcher(activity)
//        refWatcher.watch(this)
    }
}