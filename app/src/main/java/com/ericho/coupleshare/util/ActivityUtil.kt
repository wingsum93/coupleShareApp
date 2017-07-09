package com.ericho.coupleshare.util

import android.app.Activity



/**
 * Created by steve_000 on 9/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.util
 */
class ActivityUtil {
    private val activityList = ArrayList<Activity>()

    fun addActivity(activity: Activity) {
        activityList.add(activity)
    }

    fun removeActivity(activity: Activity) {
        activityList.remove(activity)
    }

    fun getTopActivity(): Activity? {
        if (activityList.isEmpty()) {
            return null
        } else {
            return activityList.get(activityList.size - 1)
        }
    }
}