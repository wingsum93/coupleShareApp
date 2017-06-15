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

import com.ericho.coupleshare.Injection;
import com.ericho.coupleshare.R;
import com.ericho.coupleshare.R2;
import com.ericho.coupleshare.http.ApiManager;
import com.ericho.coupleshare.mvp.LoginContract;
import com.ericho.coupleshare.mvp.presenter.LoginPresenter;
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
import timber.log.Timber;

public class LoginAct extends RxLifecycleAct implements View.OnClickListener,LoginContract.View{
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

    private LoginContract.Presenter mLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);
        ButterKnife.bind(this);
        init();

    }

    private void init() {

        Timber.d("init");
        mLoginPresenter = new LoginPresenter(Injection.provideLoginRepository(this),this);
        mLoginPresenter.start();
        mLoginPresenter.registerOnTextChangeListener(edt_username,edt_pw);

        btn_login.setOnClickListener(this);
        btn_register.setOnClickListener(v -> mLoginPresenter.requestRegisterPage());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.change_server_address,menu);
        getMenuInflater().inflate(R.menu.more,menu);
        return true;
    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.change_server_address:
                mLoginPresenter.displayChangeServerAddressUi();
                return true;
            case R.id.more:
                mLoginPresenter.displayMoreUi();
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


        mLoginPresenter.login(us_name,pw);
    }



    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mLoginPresenter = presenter;
    }

    @Override
    public void showLoadingIndicator(boolean active) {
        progressBar.setVisibility(active?View.VISIBLE:View.INVISIBLE);
    }

    @Override
    public void showLoginSuccess() {
        Toast.makeText(this,"Login Success",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.putExtra(RESULT_AUTHENTICATE,true);
        setResult(Activity.RESULT_OK,intent);
        finish();
    }

    @Override
    public void showLoginFailure(String errorMessage) {
        Timber.w("fail "+errorMessage);
        Toast.makeText(this, "you password is wrong", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showRegisterPage() {
        startActivity(new Intent( this,RegisterAct.class));
    }

    @Override
    public void showChangeServerAddressUi() {
        startActivity(new Intent(this,ChangeServerDialog.class));
    }

    @Override
    public void showMoreUi() {
        startActivity(new Intent(this,TestMainAct.class));
    }

    @Override
    public void showLoginButtonState(boolean enable) {
        btn_login.setEnabled(enable);
    }

    @Override
    public void resetLoginField() {
        edt_username.setText(null);
        edt_pw.setText(null);
    }

    @Override
    protected void onDestroy() {
        mLoginPresenter.destroy();
        super.onDestroy();
    }
}


