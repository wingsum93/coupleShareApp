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

import com.ericho.coupleshare.Injection;
import com.ericho.coupleshare.R;
import com.ericho.coupleshare.http.Client;
import com.ericho.coupleshare.http.GsonUtil;
import com.ericho.coupleshare.http.OkHttp;
import com.ericho.coupleshare.http.OkHttpImpl;
import com.ericho.coupleshare.http.model.BaseResponse;
import com.ericho.coupleshare.http.model.BaseSingleResponse;
import com.ericho.coupleshare.http.retrofit2.UserService;
import com.ericho.coupleshare.mvp.LoginContract;
import com.ericho.coupleshare.mvp.RegisterContract;
import com.ericho.coupleshare.mvp.presenter.RegisterPresenter;
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

public class RegisterAct extends RxLifecycleAct implements RegisterContract.View,View.OnClickListener {
    private static final String tag = "RegisterAct";

    @BindView(R.id.btn_action)
    Button btn_register;
    @BindView(R.id.edt_username)
    EditText edt_username;
    @BindView(R.id.edt_password)
    EditText edt_pw;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private UserService userService;

    private RegisterContract.Presenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_register);
        ButterKnife.bind(this);
        init();

    }

    private void init() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .client(Client.getClient())
                .baseUrl(ServerAddressUtil.getServerAddress(this))
                .build();
        userService = retrofit.create(UserService.class);

        mPresenter = new RegisterPresenter(Injection.provideLoginRepository(this),this);

        btn_register.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        mPresenter.register(edt_username.getText().toString(),edt_pw.getText().toString());
    }

    @Override
    public void setPresenter(RegisterContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showLoadingIndicator(boolean active) {
        runOnUiThread(() -> {
            progressBar.setVisibility(active?View.VISIBLE:View.INVISIBLE);
        });
    }

    @Override
    public void showRegisterSuccess() {
        runOnUiThread(() -> {
            Toast.makeText(this,"rgister Success",Toast.LENGTH_LONG).show();
        });
    }

    @Override
    public void showRegisterFailure(String errorMessage) {
        runOnUiThread(() -> {
            Toast.makeText(this,errorMessage,Toast.LENGTH_LONG).show();
        });
    }

    @Override
    public void showRegisterButtonState(boolean enable) {
        runOnUiThread(() -> {
            btn_register.setEnabled(enable);
        });
    }
}