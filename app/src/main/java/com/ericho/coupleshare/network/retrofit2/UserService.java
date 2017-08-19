package com.ericho.coupleshare.network.retrofit2;

import com.ericho.coupleshare.contant.WebAddress;
import com.ericho.coupleshare.network.model.BaseSingleResponse;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by steve_000 on 15/4/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.http.retrofit2
 */

public interface UserService {
    @POST(WebAddress.API_REGISTER)
    Observable<Response<BaseSingleResponse>> register(@Query("username") String username, @Query("password") String password);
    @POST(WebAddress.API_REGISTER)
    Call<BaseSingleResponse> register2(@Query("username") String username, @Query("password") String password);
    @POST(WebAddress.API_LOGIN)
    Observable<BaseSingleResponse> login(@Query("username") String username,@Query("password") String password);
    @POST(WebAddress.API_CHANGE_PASSWORD)
    Observable<Void> changePassword(@Body BaseSingleResponse<?> response);
}
