package com.ericho.coupleshare.data;

import android.os.Handler;
import android.os.Looper;

import com.ericho.coupleshare.mvp.data.LoginDataSource;
import com.ericho.coupleshare.util.StringUtil;

import java.util.HashMap;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by steve_000 on 12/6/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.data
 */

public class FakeLoginRemoteDataSource implements LoginDataSource {

    private static final int SERVICE_TIME_MILL = 5000;

    private static FakeLoginRemoteDataSource INSTANCE ;

    private Handler mHandler;

    private HashMap<String,String> userMap = new HashMap<>();


    private FakeLoginRemoteDataSource(){
        mHandler = new Handler(Looper.getMainLooper());
        //pre-define password
        userMap.put("peter","12345");
        userMap.put("cc","cc");
        userMap.put("eric","eric");
    }

    public static FakeLoginRemoteDataSource getInstance(){
        if(INSTANCE == null){
            INSTANCE = new FakeLoginRemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void login(String username, String password, LoginCallback callback) {
        mHandler.postDelayed(() -> {
            String theTargetPw = userMap.get(username);
            if(theTargetPw==null){
                callback.onLoginFailure(new Exception("account not exist"));
                return;
            }
            if (StringUtil.equal(theTargetPw,password)) {
                callback.onLoginSuccess();
            }else {
                callback.onLoginFailure(new Exception("password not correct"));
            }
        },SERVICE_TIME_MILL);
    }

    @Override
    public void register(String username, String password, RegisterCallback callback) {
        mHandler.postDelayed(() -> {
            String theTargetPw = userMap.get(username);
            if(theTargetPw!=null){
                callback.onRegisterFailure(new Exception("account already exist"));
                return;
            }

            userMap.put(username,password);
            callback.onRegisterSuccess();
        },SERVICE_TIME_MILL);
    }
}
