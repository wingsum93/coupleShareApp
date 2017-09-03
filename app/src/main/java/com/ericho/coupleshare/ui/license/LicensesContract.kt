package com.ericho.coupleshare.ui.license

import com.ericho.coupleshare.mvp.BasePresenter2
import com.ericho.coupleshare.mvp.BaseView

/**
 *
 * This specifies the contract between the view and the presenter.
 */

interface LicensesContract {

    interface View : BaseView<Presenter>

    interface Presenter : BasePresenter2

}