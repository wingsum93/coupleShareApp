package com.ericho.coupleshare.mvp.data;

import com.google.common.base.Preconditions;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by steve_000 on 12/6/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.mvp.data
 */

public class LoginRepository implements LoginDataSource {

    private static LoginRepository INSTANCE ;

    private LoginDataSource loginDataSource;

    private LoginRepository(LoginDataSource dataSource){
        checkNotNull(dataSource);
        this.loginDataSource = dataSource;
    }

    public static LoginRepository getInstance(LoginDataSource dataSource){
        if(INSTANCE == null){
            INSTANCE = new LoginRepository(dataSource);
        }
        return INSTANCE;
    }

    public static void destroyInstance(){

        INSTANCE = null;
    }

    @Override
    public void login(String username, String password, LoginCallback callback) {
        loginDataSource.login(username, password, callback);
    }

    @Override
    public void register(String username, String password, RegisterCallback callback) {
        loginDataSource.register(username, password, callback);
    }
}
