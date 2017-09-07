package com.ericho.coupleshare.ui

import com.ericho.coupleshare.App
import com.ericho.coupleshare.R
import com.ericho.coupleshare.constant.Constant
import com.ericho.coupleshare.network.GlideApp
import com.ericho.coupleshare.network.MyGlideModule
import com.ericho.coupleshare.util.FileUtil
import com.ericho.coupleshare.util.NetworkUtil
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.util.concurrent.Callable

/**
 * Created by steve_000 on 7/9/2017.
 * for project coupleShareApp
 * package name com.ericho.coupleshare.ui.license
 */
class SettingPresenter(
        val view: SettingContract.View
) : SettingContract.Presenter {


    lateinit var okhttpCahceFile: File
    lateinit var glideCacheFile: File
    val compositeDisposable = CompositeDisposable()

    init {
        view.setPresenter(this)
    }


    private fun getContext() = App.context!!.applicationContext!!

    override fun calCacheSize() {
        val appContext = getContext()
        val cacheParent = appContext.cacheDir
        glideCacheFile = File(cacheParent, MyGlideModule.CACHE_NAME)
        okhttpCahceFile = File(cacheParent, Constant.HTTP_CACHE_FOLDER_NAME)

        val okCacheSize = FileUtil.dirSize(okhttpCahceFile).toInt()
        val glideCacheSize = FileUtil.dirSize(glideCacheFile) .toInt()
        view.showOkhttpCacheSize(okCacheSize)
        view.showGlideCacheSize(glideCacheSize)
    }

    override fun cleanOkhttpCache() {
        val disposable = Observable.fromCallable(
                Callable {
                    return@Callable okhttpCahceFile.deleteRecursively()
                })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view.showOkhttpCacheSize(0)
                }, {
                    view.showToast("Request Not Done!")
                })
        compositeDisposable.add(disposable)
    }

    override fun cleanGlideCache() {
        val disposable = Observable.fromCallable(
                Callable {
                    return@Callable GlideApp.get(getContext()).clearDiskCache()
                })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view.showGlideCacheSize(0)
                }, {
                    view.showToast("Request Not Done!")
                })
        compositeDisposable.add(disposable)
    }

    override fun subscribe() {

    }

    override fun unsubscribe() {
        compositeDisposable.clear()
    }
}