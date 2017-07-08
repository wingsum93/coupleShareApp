package com.ericho.coupleshare.mvp.data

import android.content.Context
import android.content.SharedPreferences
import com.ericho.coupleshare.App
import com.ericho.coupleshare.contant.Key

/**
 * Created by steve_000 on 7/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.mvp.data
 */
class LoginRepository private constructor(private var dataSource: LoginDataSource):LoginDataSource {
    override fun login(username: String, password: String, callback: LoginDataSource.LoginCallback) {
        dataSource.login(username, password, object :LoginDataSource.LoginCallback{
            override fun onLoginSuccess() {
                logLoginAction(App.context,username)
                callback.onLoginSuccess()
            }

            override fun onLoginFailure(t: Throwable) {
                callback.onLoginFailure(t)
            }
        })
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

    fun isLogin(context: Context):Boolean{
        val pref =  getPref(context)
        val res = pref?.getBoolean(Key.login,false) ?:false
        return res
    }
    fun getLoginName(context: Context):String{
        val pref =  getPref(context)
        val res = pref?.getString(Key.loginName,"") ?:""
        return res
    }

    fun logLoginAction(context: Context?,name:String){
        val pref =  getPref(context)
        pref?.edit()
                ?.putBoolean(Key.login,true)
                ?.putString(Key.loginName,name)
                ?.apply()
    }
    fun logout(context: Context){
        val pref = getPref(context)
        pref?.edit()
                ?.remove(Key.login)
                ?.remove(Key.loginName)
                ?.apply()
    }

    fun getPref(context: Context?):SharedPreferences?{
        return context?.getSharedPreferences(Key.pref_name,0)
    }
}