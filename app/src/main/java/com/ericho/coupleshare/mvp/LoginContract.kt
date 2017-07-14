package com.ericho.coupleshare.mvp

import android.widget.Button
import android.widget.EditText

/**
 * Created by steve_000 on 8/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.mvp
 */
interface LoginContract {
    interface View : BaseView<Presenter> {

        fun showLoadingIndicator(active: Boolean)

        fun showLoginSuccess()

        fun showLoginFailure(errorMessage: String?)

        fun showRegisterPage()

        fun showChangeServerAddressUi()

        fun showMoreUi()

        fun showLoginButtonState(enable: Boolean)

        fun resetLoginField()
    }

    interface Presenter : BasePresenter {

        fun login(username: String, password: String)

        fun reset()

        fun requestRegisterPage()

        fun registerOnTextChangeListener(usernameEditText: EditText,
                                         passwordEditText: EditText,
                                        loginBtn:Button)

        fun displayChangeServerAddressUi()

        fun displayMoreUi()

        fun destroy()
    }
}