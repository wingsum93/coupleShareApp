package com.ericho.coupleshare.http;

import android.content.Context;
import android.util.Log;

import com.ericho.coupleshare.contant.WebAddress;
import com.ericho.coupleshare.util.ServerAddressUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by steve_000 on 17/4/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.http
 */

public class OkHttpImpl implements OkHttp{
    private static final String tag = "OkHttpImpl";
    private static OkHttpClient okHttpClient;
    private static OkHttp okHttp;


    public static OkHttp getInstance(){
        if(okHttp ==null){
            synchronized (OkHttpImpl.class){
                okHttp = new OkHttpImpl();
            }
        }
        return okHttp;
    }
    private OkHttpImpl (){
        if(okHttpClient ==null){
            synchronized (OkHttpImpl.class){
                okHttpClient = new OkHttpClient.Builder()
                        .readTimeout(10, TimeUnit.SECONDS)
                        .writeTimeout(10,TimeUnit.SECONDS)
                        .retryOnConnectionFailure(false)
                        .build();
            }
        }
    }



    //----------------------------------------------------------
    public String getUrl(Context context,String suffix){
        return ServerAddressUtil.getServerAddress(context)+suffix;
    }

    //----------------------------------------------------------
    @Override
    public String registerUser(Context context,String username, String password) throws IOException{

        RequestBody body = new FormBody.Builder()
                .add("username",username)
                .add("password",password)
                .build();
        Request request = new Request.Builder()
                .url(getUrl(context,WebAddress.API_REGISTER))
                .post(body)
                .build();

        Response response = okHttpClient.newCall(request).execute();
        if (!response.isSuccessful()) {
            response.body().close();
            Log.w(tag,WebAddress.API_REGISTER+" fail with code "+response.code());
            throw new IOException("fail with status code = "+response.code());
        }
        String responseString = response.body().string();
        return responseString;
    }


}
