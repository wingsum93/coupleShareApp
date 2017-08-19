package com.ericho.coupleshare.util

import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.reward.RewardedVideoAd


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


fun AppCompatActivity.loadRewardedVideoAd(  ads_id:String,mAd:RewardedVideoAd):Unit{
    val req = AdRequest.Builder()
//            .addTestDevice("54464B6ADF6F4418B70974F9BC194FC0")
            .build()
    mAd.loadAd(ads_id, req)
}
