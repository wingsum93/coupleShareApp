package com.ericho.coupleshare.ui

import com.ericho.coupleshare.mvp.BasePresenter
import com.ericho.coupleshare.mvp.BasePresenter2
import com.ericho.coupleshare.mvp.BaseView

/**
 * Created by steve_000 on 7/9/2017.
 * for project coupleShareApp
 * package name com.ericho.coupleshare.ui
 */
interface SettingContract {

    interface View : BaseView<Presenter> {
        /**
         * @param size the size in MB
         */
        fun showOkhttpCacheSize(size: Int)
        /**
         * @param size the size in MB
         */
        fun showGlideCacheSize(size: Int)
        fun showToast(str: String)
    }

    interface Presenter : BasePresenter2 {
        fun calCacheSize()
        fun cleanOkhttpCache()
        fun cleanGlideCache()
    }
}