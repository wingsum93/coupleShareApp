package com.ericho.coupleshare.network

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpLibraryGlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import com.ericho.coupleshare.util.NetworkUtil
import java.io.File
import java.io.InputStream

/**
 * Created by steve_000 on 7/9/2017.
 * for project coupleShareApp
 * package name com.ericho.coupleshare.network
 */
@GlideModule
class MyGlideModule : AppGlideModule() {
    override fun applyOptions(context: Context?, builder: GlideBuilder?) {

        builder!!.setMemoryCache(LruResourceCache(MAX_MEMORY_SIZE))
        builder.setDiskCache(InternalCacheDiskCacheFactory(context, "glide_cache", MAX_DISK_CACHE_SIZE))
        builder.setDefaultRequestOptions(RequestOptions().format(DecodeFormat.PREFER_ARGB_8888).disallowHardwareConfig())

    }

    override fun isManifestParsingEnabled(): Boolean = false
    override fun registerComponents(context: Context?, glide: Glide?, registry: Registry?) {
        registry!!.replace(GlideUrl::class.java,InputStream::class.java,OkHttpUrlLoader.Factory(NetworkUtil.getOkhttpClient()))
    }

    companion object {
        @JvmField
        val MAX_MEMORY_SIZE = 1024 * 1024 * 40
        @JvmField
        val MAX_DISK_CACHE_SIZE = 1024 * 1024 * 180
        @JvmField
        val CACHE_NAME = "glide_cache"
    }
}