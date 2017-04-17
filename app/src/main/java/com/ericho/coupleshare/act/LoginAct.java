package com.ericho.coupleshare.act;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ericho.coupleshare.R;
import com.ericho.coupleshare.http.Client;
import com.ericho.coupleshare.http.Login;
import com.ericho.coupleshare.http.retrofit2.UserService;
import com.ericho.coupleshare.util.ServerAddressUtil;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginAct extends RxLifecycleAct implements View.OnClickListener{
    private static final String tag = "LoginConsumerActivity";
    public static final String RESULT_AUTHENTICATE = "RESULT_AUTHENTICATE";
    @BindView(R.id.btn_login)
    Button btn_login;
    @BindView(R.id.btn_register)
    Button btn_register;
    @BindView(R.id.edt_username)
    EditText edt_username;
    @BindView(R.id.edt_password)
    EditText edt_pw;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);
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

        btn_login.setOnClickListener(this);
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
                        btn_login.setEnabled(aBoolean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(LoginAct.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        Log.d(tag, "cc complete");
                    }
                });


        //for register page
        RxView.clicks(btn_register)
                .debounce(300, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifeCycle())
                .subscribe(o -> {
                    startActivity(new Intent( this,RegisterAct.class));
                });
    }

    private void LoginInSuccess() {
        Toast.makeText(this,"Login Success",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.putExtra(RESULT_AUTHENTICATE,true);
        setResult(Activity.RESULT_OK,intent);
        finish();
    }


    @Override
    public void onClick(View v) {
        //login api
        String us_name = edt_username.getText().toString();
        String pw = edt_pw.getText().toString();
        userService.login(us_name, pw)
                .doOnNext(o -> {
                    lockScreen(true);
                })

                .subscribeOn(Schedulers.io())
                .compose(this.bindToLifeCycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseSingleResponse -> {
                    lockScreen(false);
                    if (baseSingleResponse.isStatus()) {
                        LoginInSuccess();
                    } else {
                        Toast.makeText(this, "you password is wrong", Toast.LENGTH_LONG).show();
                    }
                }, err -> {
                    lockScreen(false);
                    Log.d(tag, "error"+err.getMessage());
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


