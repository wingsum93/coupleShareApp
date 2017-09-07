package com.ericho.coupleshare.network

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.ericho.coupleshare.R

/**
 * Created by steve_000 on 7/9/2017.
 * for project coupleShareApp
 * package name com.ericho.coupleshare.network
 */
object GlideLoader {


    fun loadNormal(context: Context,target: ImageView, url: String) {
        GlideApp.with(context)
                .asBitmap()
                .placeholder(R.drawable.ic_orange)
                .error(R.drawable.warning)
                .centerCrop()
                .load(url)
                .into(target)
    }
    fun loadWithHighPriority(context: Context,target: ImageView, url: String) {
        GlideApp.with(context)
                .asBitmap()
                .placeholder(R.drawable.ic_orange)
                .error(R.drawable.warning)
                .priority(Priority.HIGH)
                .centerCrop()
                .load(url)
                .into(target)
    }
    fun loadIconPicture(context: Context, target: ImageView, url: String) {
        GlideApp.with(context)
                .asBitmap()
                .error(R.drawable.warning)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .circleCrop()
                .load(url)
                .into(target)

    }


}