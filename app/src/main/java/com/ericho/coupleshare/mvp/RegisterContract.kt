package com.ericho.coupleshare.mvp

import android.widget.Button
import android.widget.EditText

/**
 * Created by steve_000 on 8/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.mvp
 */
interface RegisterContract {
    interface View : BaseView<Presenter> {

        fun showLoadingIndicator(active: Boolean)

        fun showRegisterSuccess()

        fun showRegisterFailure(errorMessage: String?)

        fun showRegisterButtonState(enable: Boolean)

    }

    interface Presenter : BasePresenter {

        fun register(username: String, password: String)

        fun reset()

        fun assignOnTextChangeListener(usernameEditText: EditText,
                                       passwordEditText: EditText,
                                        loginButton:Button)

        fun destroy()
    }
}