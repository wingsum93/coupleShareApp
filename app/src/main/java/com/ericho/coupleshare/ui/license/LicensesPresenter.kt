package com.ericho.coupleshare.ui.license

/**
 *
 * Listens to user action from the ui [LicensesFragment],
 * retrieves the data and update the ui as required.
 */

class LicensesPresenter(view: LicensesContract.View) : LicensesContract.Presenter {

    private val mView = view

    init {
        mView.setPresenter(this)
    }

    override fun subscribe() {

    }

    override fun unsubscribe() {

    }

}