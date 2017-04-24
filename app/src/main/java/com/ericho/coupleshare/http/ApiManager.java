package com.ericho.coupleshare.http;

import com.ericho.coupleshare.Application;
import com.ericho.coupleshare.http.model.BaseSingleResponse;
import com.ericho.coupleshare.http.retrofit2.UserService;
import com.ericho.coupleshare.util.ServerAddressUtil;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Query;

/**
 * Created by EricH on 24/4/2017.
 */

public class ApiManager implements UserService{
    private static ApiManager instance;

    private OkHttpClient okHttpClient;
    private Retrofit retrofit;
    //api's service
    private UserService userService;

    public static ApiManager getInstance(){
        if(instance==null){
            instance = new ApiManager();
        }
        return instance;
    }

    public ApiManager() {
        okHttpClient = new OkHttpClient.Builder().build();
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .client(okHttpClient)
                .baseUrl(ServerAddressUtil.getServerAddress(Application.getContext()))
                .build();


        userService = retrofit.create(UserService.class);

    }

    @Override
    public Observable<Response<BaseSingleResponse>> register(@Query("username") String username, @Query("password") String password) {
        return userService.register(username, password);
    }

    @Override
    public Call<BaseSingleResponse> register2(@Query("username") String username, @Query("password") String password) {
        return userService.register2(username, password);
    }

    @Override
    public Observable<BaseSingleResponse> login(@Query("username") String username, @Query("password") String password) {
        return userService.login(username, password);
    }

    @Override
    public Observable<Void> changePassword(@Body BaseSingleResponse<?> response) {
        return userService.changePassword(response);
    }

    public void changeBaseUrl(String dataUrl) {
        Retrofit newRetrofit = retrofit.newBuilder().baseUrl(dataUrl)
                .build();
        retrofit = newRetrofit;
    }
}
