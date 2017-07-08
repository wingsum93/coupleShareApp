package com.ericho.coupleshare.mvp.data

/**
 * Created by steve_000 on 7/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.mvp.data
 */
class LoginRepository private constructor(private var dataSource: LoginDataSource):LoginDataSource {
    override fun login(username: String, password: String, callback: LoginDataSource.LoginCallback) {
        dataSource.login(username, password, callback)
    }

    override fun register(username: String, password: String, callback: LoginDataSource.RegisterCallback) {
        dataSource.register(username, password, callback)
    }

    companion object {
        private var INSTANCE : LoginRepository? = null


        @JvmStatic
        fun getInstance(dataSource: LoginDataSource):LoginRepository{
            if (INSTANCE == null) {
                INSTANCE = LoginRepository(dataSource)
            }
            return INSTANCE as LoginRepository
        }
        @JvmStatic
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}