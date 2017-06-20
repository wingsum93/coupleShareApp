package com.ericho.coupleshare.mvp.presenter;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.widget.EditText;

import com.ericho.coupleshare.mvp.LoginContract;
import com.ericho.coupleshare.mvp.RegisterContract;
import com.ericho.coupleshare.mvp.data.LoginDataSource;
import com.ericho.coupleshare.mvp.data.LoginRepository;
import com.jakewharton.rxbinding2.widget.RxTextView;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by steve_000 on 14/6/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.mvp.presenter
 */

public class RegisterPresenter implements RegisterContract.Presenter {

    private LoginRepository loginRepository;

    private RegisterContract.View mView;

    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private Observable<Boolean> observable;
    private Disposable disposable;
    private Handler mHandler ;

    public RegisterPresenter(@NonNull LoginRepository tasksRepository, @NonNull RegisterContract.View registerView) {
        loginRepository = checkNotNull(tasksRepository, "tasksRepository cannot be null");
        mView = checkNotNull(registerView, "view cannot be null!");
        mView.setPresenter(this);
        mHandler = new Handler();
    }

    @Override
    public void start() {
        //init state action
        mView.showRegisterButtonState(false);
    }

    @Override
    public void register(String username, String password) {
        mView.showRegisterButtonState(false);
        mView.showLoadingIndicator(true);
        mUsernameEditText.setEnabled(false);
        mPasswordEditText.setEnabled(false);

        loginRepository.register(username, password, new LoginDataSource.RegisterCallback() {
            @Override
            public void onRegisterSuccess() {
                mView.showRegisterButtonState(true);
                mView.showLoadingIndicator(false);
                mUsernameEditText.setEnabled(true);
                mPasswordEditText.setEnabled(true);
                //
                mView.showRegisterSuccess();
            }

            @Override
            public void onRegisterFailure(Throwable t) {
                mView.showRegisterButtonState(true);
                mView.showLoadingIndicator(false);
                mUsernameEditText.setEnabled(true);
                mPasswordEditText.setEnabled(true);
                //
                mView.showRegisterFailure(t.getMessage());

            }
        });
    }

    @Override
    public void reset() {

    }

    @Override
    public void assignOnTextChangeListener(@NonNull EditText usernameEditText, @NonNull EditText passwordEditText) {
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
                                    Timber.d("login X btn should be "+aBoolean);
                                    mView.showRegisterButtonState(aBoolean);
                                },
                                throwable -> Timber.w(throwable),

                                () -> Timber.d("onComplete")
                        );
    }

    @Override
    public void destroy() {
        if(disposable!=null){
            disposable.dispose();
        }
    }
}
