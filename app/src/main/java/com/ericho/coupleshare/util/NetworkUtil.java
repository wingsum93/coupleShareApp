package com.ericho.coupleshare.util;

import android.content.Context;

import com.ericho.coupleshare.contant.WebAddress;
import com.ericho.coupleshare.setting.ServerAddress;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by steve_000 on 31/5/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.util
 */

public class NetworkUtil {

    private static OkHttpClient okhttpClient;

    public static OkHttpClient getOkhttpClient(){

        if(okhttpClient==null){
            okhttpClient = new OkHttpClient.Builder()
                    .readTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10,TimeUnit.SECONDS)
                    .connectTimeout(10,TimeUnit.SECONDS)
                    .build();
        }

        return okhttpClient;
    }

    public static Request getXXXRequest(String ss){
        Request.Builder builder = new Request.Builder();
        builder.addHeader("XXX","XXX");
//        builder.post()
        return builder.build();
    }

    public static Call execute(OkHttpClient okHttpClient,Request request){
        return okHttpClient.newCall(request);
    }




    public Response getResponseByRequest(Call call) throws IOException{
        Response response =
                call.execute();
        if(!response.isSuccessful()) throw new IOException(response.message());
        return response;
    }

    public static String parseResponseToString(Response response) throws IOException{
        if(!response.isSuccessful()) throw new IOException(response.message());
        return response.body().string();
    }

    //------------------------------------------------------------------------------

    public static Request registerAccount(Context context,String username, String password) throws IOException{
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("username",username);
        builder.add("password",password);

        Request.Builder b = new Request.Builder();
        b.post(builder.build());
        b.url(HttpUrl.parse(getUrl(context, WebAddress.API_REGISTER)));
        return b.build();
    }
    public static Request loginAccount(Context context,String username, String password) throws IOException{
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("username",username);
        builder.add("password",password);

        Request.Builder b = new Request.Builder();
        b.post(builder.build());
        b.url(HttpUrl.parse(getUrl(context, WebAddress.API_LOGIN)));
        return b.build();
    }

    public static String getUrl(Context context,String suffix){

        String s = ServerAddressUtil.getServerAddress(context) +  suffix;
        return s;
    }
}
