package com.ericho.coupleshare.mvp.data

/**
 * Created by steve_000 on 6/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.mvp.data
 */
interface LoginDataSource {
    interface LoginCallback {
        fun onLoginSuccess()
        fun onLoginFailure(t: Throwable)
    }

    interface RegisterCallback {
        fun onRegisterSuccess()
        fun onRegisterFailure(t: Throwable)
    }

    fun login(username: String, password: String, callback: LoginCallback)

    fun register(username: String, password: String, callback: RegisterCallback)
}