package com.ericho.coupleshare.mvp

/**
 * Created by steve_000 on 4/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.mvp
 */
interface StatusContract{
    interface View : BaseView<Presenter>
    interface Presenter : BasePresenter
}