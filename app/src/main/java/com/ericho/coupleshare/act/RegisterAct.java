package com.ericho.coupleshare.act;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ericho.coupleshare.Injection;
import com.ericho.coupleshare.R;
import com.ericho.coupleshare.http.retrofit2.UserService;
import com.ericho.coupleshare.mvp.RegisterContract;
import com.ericho.coupleshare.mvp.presenter.RegisterPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

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

        mPresenter = new RegisterPresenter(Injection.provideLoginRepository(this),this);

        btn_register.setOnClickListener(this);

        edt_pw.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                String us_name = edt_username.getText().toString();
                String pw = edt_pw.getText().toString();
                mPresenter.register(us_name,pw);
                return true;
            }
            return false;
        });
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