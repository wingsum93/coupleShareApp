package com.ericho.coupleshare.act;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ericho.coupleshare.R;
import com.ericho.coupleshare.R2;
import com.ericho.coupleshare.http.ApiManager;
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

public class LoginAct extends RxLifecycleAct implements View.OnClickListener{
    private static final String tag = "LoginAct";
    public static final String RESULT_AUTHENTICATE = "RESULT_AUTHENTICATE";
    @BindView(R2.id.btn_login)
    Button btn_login;
    @BindView(R2.id.btn_register)
    Button btn_register;
    @BindView(R2.id.edt_username)
    EditText edt_username;
    @BindView(R2.id.edt_password)
    EditText edt_pw;
    @BindView(R2.id.progressBar)
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);
        ButterKnife.bind(this);
        init();
        setObservable();

    }

    private void init() {

        btn_login.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.change_server_address,menu);
        return true;
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.change_server_address:
                startActivity(new Intent(this,ChangeServerDialog.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        //login api
        String us_name = edt_username.getText().toString();
        String pw = edt_pw.getText().toString();
        ApiManager.getInstance().login(us_name, pw)
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


