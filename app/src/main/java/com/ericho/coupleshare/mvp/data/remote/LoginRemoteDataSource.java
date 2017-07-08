package com.ericho.coupleshare.mvp.data.remote;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;

import com.ericho.coupleshare.http.BaseUiCallback;
import com.ericho.coupleshare.http.GsonUtil;
import com.ericho.coupleshare.http.model.BaseSingleResponse;
import com.ericho.coupleshare.mvp.data.LoginDataSource;
import com.ericho.coupleshare.util.NetworkUtil;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.SocketTimeoutException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by steve_000 on 12/6/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.mvp.data.remote
 */

public class LoginRemoteDataSource implements LoginDataSource {

    private static LoginRemoteDataSource INSTANCE ;

    private HandlerThread mHandlerThread;

    private Handler mHandler;

    private OkHttpClient client;

    private Context context;

    private LoginRemoteDataSource(Context context){
        this.context = context;
        mHandlerThread = new HandlerThread("user-remote");
        mHandlerThread.start();
        mHandler = new Handler(mHandlerThread.getLooper());
        client = NetworkUtil.getOkhttpClient();
    }

    public static LoginRemoteDataSource getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = new LoginRemoteDataSource(context);
        }
        return INSTANCE;
    }

    @Override
    public void login(String username, String password, LoginCallback callback) {
        mHandler.post(() -> {
            try {
                Request request = NetworkUtil.loginAccount(context,username,password);
                Call call = NetworkUtil.execute(client,request);
                Response response = call.execute();
                String res = NetworkUtil.parseResponseToString(response);
                Timber.d(res);
                Type type = new TypeToken<BaseSingleResponse<String>>(){}.getType();
                BaseSingleResponse<String> baseSingleResponse = GsonUtil.getGson().fromJson(res,type);
                if (baseSingleResponse.getStatus()) {
                    callback.onLoginSuccess();
                }else {
                    callback.onLoginFailure(new IOException(baseSingleResponse.getErrorMessage()));
                }
            } catch (SocketTimeoutException e) {
                callback.onLoginFailure(e);
            }catch (IOException e) {
                e.printStackTrace();
                callback.onLoginFailure(e);
            }
        });


    }

    @Override
    public void register(String username, String password, RegisterCallback callback) {
        mHandler.post(() -> {
            try {
                Request request = NetworkUtil.registerAccount(context,username,password);
                Call call = NetworkUtil.execute(client,request);
                Response response = call.execute();
                String res = NetworkUtil.parseResponseToString(response);

                Type type = new TypeToken<BaseSingleResponse<String>>(){}.getType();
                BaseSingleResponse<String> baseSingleResponse = GsonUtil.getGson().fromJson(res,type);
                if (baseSingleResponse.getStatus()) {
                    callback.onRegisterSuccess();
                }else {
                    callback.onRegisterFailure(new IOException(baseSingleResponse.getErrorMessage()));
                }
            } catch (IOException e) {
                e.printStackTrace();
                callback.onRegisterFailure(e);
            }
        });
    }
}
