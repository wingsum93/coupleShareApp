package com.ericho.coupleshare.mvp.presenter;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.widget.EditText;

import com.ericho.coupleshare.mvp.LoginContract;
import com.ericho.coupleshare.mvp.data.LoginDataSource;
import com.ericho.coupleshare.mvp.data.LoginRepository;
import com.jakewharton.rxbinding2.widget.RxTextView;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by steve_000 on 12/6/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.mvp.presenter
 */

public class LoginPresenter implements LoginContract.Presenter {

    private LoginRepository loginRepository;

    private LoginContract.View mLoginView;

    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private Observable<Boolean> observable;
    private Disposable disposable;
    private Handler mHandler;

    public LoginPresenter(@NonNull LoginRepository tasksRepository, @NonNull LoginContract.View tasksView) {
        loginRepository = checkNotNull(tasksRepository, "tasksRepository cannot be null");
        mLoginView = checkNotNull(tasksView, "tasksView cannot be null!");
        mLoginView.setPresenter(this);
        mHandler = new Handler();
    }

    @Override
    public void start() {
        mLoginView.showLoginButtonState(false);
    }

    @Override
    public void login(String username, String password) {

        mLoginView.showLoadingIndicator(true);
        mUsernameEditText.setEnabled(false);
        mPasswordEditText.setEnabled(false);
        //
        loginRepository.login(username, password, new LoginDataSource.LoginCallback() {
            @Override
            public void onLoginSuccess() {
                mHandler.post(() -> {
                    mLoginView.showLoginSuccess();
                    mLoginView.showLoadingIndicator(false);
                    mUsernameEditText.setEnabled(true);
                    mPasswordEditText.setEnabled(true);
                });

            }

            @Override
            public void onLoginFailure(Throwable t) {
                mHandler.post(() -> {
                    mLoginView.showLoginFailure(t.getMessage());
                    mLoginView.showLoadingIndicator(false);
                    mUsernameEditText.setEnabled(true);
                    mPasswordEditText.setEnabled(true);
                });
            }
        });
    }

    @Override
    public void reset() {
        mLoginView.resetLoginField();
    }

    @Override
    public void requestRegisterPage() {
        Timber.d("requestRegisterPage");
        mLoginView.showRegisterPage();
    }

    @Override
    public void registerOnTextChangeListener(@NonNull EditText usernameEditText, @NonNull EditText passwordEditText) {
        mUsernameEditText = checkNotNull(usernameEditText);
        mPasswordEditText = checkNotNull(passwordEditText);

        Observable<String> usernameObservable =
                RxTextView.afterTextChangeEvents(mUsernameEditText).map(ev -> ev.editable().toString());
        Observable<String> passwordObservable =
                RxTextView.afterTextChangeEvents(mPasswordEditText).map(ev -> ev.editable().toString());
        observable =
                Observable.zip(usernameObservable, passwordObservable,
                        (user, pw) -> user.length() > 0 && pw.length() > 0);
        disposable =
                observable
                        .subscribe(
                                aBoolean -> {
                                    Timber.d("login X btn should be " + aBoolean);
                                    mLoginView.showLoginButtonState(aBoolean);
                                },
                                throwable -> Timber.w(throwable),

                                () -> Timber.d("onComplete")
                        );

    }

    @Override
    public void displayChangeServerAddressUi() {
        mLoginView.showChangeServerAddressUi();
    }

    @Override
    public void displayMoreUi() {
        mLoginView.showMoreUi();
    }

    @Override
    public void destroy() {
        if (disposable != null) {
            disposable.dispose();
        }
    }
}
