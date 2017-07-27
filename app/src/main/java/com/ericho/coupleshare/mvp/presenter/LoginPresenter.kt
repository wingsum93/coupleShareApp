package com.ericho.coupleshare.mvp.presenter

import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
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
    private var mLoginBtn: Button? = null
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

    override fun registerOnTextChangeListener(usernameEditText: EditText, passwordEditText: EditText, loginBtn: Button) {
        mUsernameEditText = usernameEditText
        mPasswordEditText = passwordEditText
        mLoginBtn = loginBtn

        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                checkShouldEnableLogin()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        }
        mUsernameEditText?.addTextChangedListener(textWatcher)
        mPasswordEditText?.addTextChangedListener(textWatcher)
    }

    fun checkShouldEnableLogin(){
        val enableBtn =
                mUsernameEditText!!.text.isNotBlank() &&
                        mPasswordEditText!!.text.isNotBlank()

        mLoginBtn?.isEnabled = enableBtn
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