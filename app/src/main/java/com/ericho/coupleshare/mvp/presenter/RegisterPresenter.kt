package com.ericho.coupleshare.mvp.presenter

import android.os.Handler
import android.widget.EditText
import com.ericho.coupleshare.mvp.RegisterContract
import com.ericho.coupleshare.mvp.data.LoginDataSource
import com.ericho.coupleshare.mvp.data.LoginRepository
import io.reactivex.Observable
import io.reactivex.disposables.Disposable

/**
 * Created by steve_000 on 7/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.mvp.presenter
 */
class RegisterPresenter : RegisterContract.Presenter {

    private var loginRepository: LoginRepository

    private var mView: RegisterContract.View

    private var mUsernameEditText: EditText? = null
    private var mPasswordEditText: EditText? = null
    private var observable: Observable<Boolean>? = null
    private var disposable: Disposable? = null
    private var mHandler: Handler

    constructor(tasksRepository: LoginRepository, registerView: RegisterContract.View) {
        loginRepository = tasksRepository
        mView = registerView
        mView.setPresenter(this)
        mHandler = Handler()
    }

    override fun start() {
        //init state action
        mView.showRegisterButtonState(false)
    }

    override fun register(username: String, password: String) {
        mView.showRegisterButtonState(false)
        mView.showLoadingIndicator(true)
        mUsernameEditText?.isEnabled = false
        mPasswordEditText?.isEnabled = false

        loginRepository.register(username, password, object : LoginDataSource.RegisterCallback {
            override fun onRegisterSuccess() {
                mHandler.post {
                    mView.showRegisterButtonState(true)
                    mView.showLoadingIndicator(false)
                    mUsernameEditText?.isEnabled = true
                    mPasswordEditText?.isEnabled = true
                    //
                    mView.showRegisterSuccess()
                }

            }

            override fun onRegisterFailure(t: Throwable) {
                mHandler.post{
                    mView.showRegisterButtonState(true)
                    mView.showLoadingIndicator(false)
                    mUsernameEditText?.isEnabled = true
                    mPasswordEditText?.isEnabled = true
                    //
                    mView.showRegisterFailure(t.message)
                }
            }
        })
    }

    override fun reset() {

    }

    override fun assignOnTextChangeListener(usernameEditText: EditText, passwordEditText: EditText) {
        mUsernameEditText = checkNotNull(usernameEditText)
        mPasswordEditText = checkNotNull(passwordEditText)

       //todo after text change listener
    }

    override fun destroy() {
        if (disposable != null) {
            disposable!!.dispose()
        }
    }
}