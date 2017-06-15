package com.ericho.coupleshare.mvp.data;

/**
 * Created by steve_000 on 12/6/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.mvp.data
 */

public interface LoginDataSource {

    interface LoginCallback{
        void onLoginSuccess();
        void onLoginFailure(Throwable t);
    }
    interface RegisterCallback{
        void onRegisterSuccess();
        void onRegisterFailure(Throwable t);
    }

    void login(String username,String password,LoginCallback callback);

    void register(String username,String password,RegisterCallback callback);

}
