package com.ericho.coupleshare.act;

import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ericho.coupleshare.R;
import com.ericho.coupleshare.http.Client;
import com.ericho.coupleshare.http.GsonUtil;
import com.ericho.coupleshare.http.OkHttp;
import com.ericho.coupleshare.http.OkHttpImpl;
import com.ericho.coupleshare.http.model.BaseResponse;
import com.ericho.coupleshare.http.model.BaseSingleResponse;
import com.ericho.coupleshare.http.retrofit2.UserService;
import com.ericho.coupleshare.util.ServerAddressUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.lang.reflect.Type;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by steve_000 on 15/4/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.act
 */

public class RegisterAct extends RxLifecycleAct implements View.OnClickListener {
    private static final String tag = "RegisterAct";
    @BindView(R.id.btn_action)
    Button btn_register;
    @BindView(R.id.edt_username)
    EditText edt_username;
    @BindView(R.id.edt_password)
    EditText edt_pw;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    private Gson gson = new Gson();
    private boolean processing = false;
    private UserService userService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_register);
        ButterKnife.bind(this);
        init();
        setObservable();

    }

    private void init() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .client(Client.getClient())
                .baseUrl(ServerAddressUtil.getServerAddress(this))
                .build();
        userService = retrofit.create(UserService.class);

        btn_register.setOnClickListener(this);
    }

    private void setObservable() {
        Observable<String> usernameObservable =
                RxTextView.afterTextChangeEvents(edt_username).map(ev -> ev.editable().toString());
        Observable<String> passwordObservable =
                RxTextView.afterTextChangeEvents(edt_pw).map(ev -> ev.editable().toString());

        Observable.zip(usernameObservable, passwordObservable,
                (user, pw) -> user.length() > 0 && pw.length() > 0)
                .compose(this.bindToLifeCycle())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        btn_register.setEnabled(aBoolean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(RegisterAct.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        Log.d(tag, "cc complete");
                    }
                });

        //register api


    }

    private void onRegisterSuccess() {
        runOnUiThread(() -> {
            Toast.makeText(this, "register success.", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() != R.id.btn_action) return;

        lockScreen(false);


        Observable<Response<BaseSingleResponse>> observable2 = Observable.fromCallable(()->{
            Call<BaseSingleResponse> call = userService.register2(edt_username.getText().toString(), edt_pw.getText().toString());
            Response<BaseSingleResponse> responseResponse = call.execute();
            return responseResponse;
        });
        Observable<BaseSingleResponse> observable3 = Observable.fromCallable(()->{
            String res = OkHttpImpl.getInstance().registerUser(RegisterAct.this,edt_username.getText().toString(), edt_pw.getText().toString());
            Log.d(tag,"XXAE "+res);
            Type type = new TypeToken<BaseSingleResponse>(){}.getType();
            BaseSingleResponse baseSingleResponse = GsonUtil.getGson().fromJson(res,type);
            return baseSingleResponse;
        });

        Observable<Response<BaseSingleResponse>> observable = userService.register(edt_username.getText().toString(), edt_pw.getText().toString());
        observable3
                .compose(this.bindToLifeCycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseSingleResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(BaseSingleResponse res1) {
                        Log.d(tag, " next in main? " + Looper.getMainLooper().equals(Looper.myLooper()));
                        BaseSingleResponse res= res1;
                        Log.d(tag, "ggg " + GsonUtil.getGson().toJson(res));
                        lockScreen(false);
                        if (res.isStatus()) {
                            onRegisterSuccess();
                        } else {
                            showErrorMessage(res.getErrorMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        lockScreen(false);
                        Log.d(tag, "error " + e.getMessage());
                        Log.d(tag, "error In Main " + Looper.getMainLooper().equals(Looper.myLooper()));
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void showErrorMessage(final String err) {
        runOnUiThread(() -> {
            Toast.makeText(RegisterAct.this, err, Toast.LENGTH_SHORT).show();//backgroundd thread
        });

    }

    private void lockScreen(boolean processing) {
        runOnUiThread(() -> {
            if (processing) {
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.INVISIBLE);
            }
            btn_register.setEnabled(!processing);
        });
    }
}