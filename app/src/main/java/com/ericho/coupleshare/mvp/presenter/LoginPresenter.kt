package com.ericho.coupleshare.mvp.presenter

import android.os.Handler
import android.widget.EditText
import com.ericho.coupleshare.mvp.LoginContract
import com.ericho.coupleshare.mvp.data.LoginDataSource
import com.ericho.coupleshare.mvp.data.LoginRepository
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import timber.log.Timber

/**
 * Created by steve_000 on 7/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.mvp.presenter
 */
class LoginPresenter: LoginContract.Presenter {
    private var loginRepository: LoginRepository

    private var mLoginView: LoginContract.View

    private var mUsernameEditText: EditText? = null
    private var mPasswordEditText: EditText? = null
    private var observable: Observable<Boolean>? = null
    private var disposable: Disposable? = null
    private var mHandler: Handler

    constructor(tasksRepository: LoginRepository, tasksView: LoginContract.View){
        loginRepository = tasksRepository
        mLoginView = tasksView
        mLoginView.setPresenter(this)
        mHandler = Handler()
    }

    override fun start() {
        mLoginView.showLoginButtonState(false)
    }

    override fun login(username: String, password: String) {
        mUsernameEditText!!.error = null
        mPasswordEditText!!.error = null
        mLoginView.showLoadingIndicator(true)
        mUsernameEditText!!.isEnabled = false
        mPasswordEditText!!.isEnabled = false
        //
        loginRepository.login(username, password, object : LoginDataSource.LoginCallback {
            override fun onLoginSuccess() {
                mHandler.post {
                    mLoginView.showLoginSuccess()
                    mLoginView.showLoadingIndicator(false)
                    mUsernameEditText!!.isEnabled = true
                    mPasswordEditText!!.isEnabled = true
                }

            }

            override fun onLoginFailure(t: Throwable) {
                mHandler.post {
                    mLoginView.showLoginFailure(t.message)
                    mLoginView.showLoadingIndicator(false)
                    mUsernameEditText!!.isEnabled = true
                    mPasswordEditText!!.isEnabled = true
                }
            }
        })
    }

    override fun reset() {
        mLoginView.resetLoginField()
    }

    override fun requestRegisterPage() {
        Timber.d("requestRegisterPage")
        mLoginView.showRegisterPage()
    }

    override fun registerOnTextChangeListener(usernameEditText: EditText, passwordEditText: EditText) {
        mUsernameEditText = checkNotNull(usernameEditText)
        mPasswordEditText = checkNotNull(passwordEditText)


       //todo

    }

    override fun displayChangeServerAddressUi() {
        mLoginView.showChangeServerAddressUi()
    }

    override fun displayMoreUi() {
        mLoginView.showMoreUi()
    }

    override fun destroy() {
        if (disposable != null) {
            disposable!!.dispose()
        }
    }
}