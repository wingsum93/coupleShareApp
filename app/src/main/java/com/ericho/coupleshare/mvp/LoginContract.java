package com.ericho.coupleshare.mvp;

import android.support.annotation.NonNull;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by steve_000 on 12/6/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.mvp
 */

public interface LoginContract {
    interface View extends BaseView<Presenter>{

        void showLoadingIndicator(boolean active);

        void showLoginSuccess();

        void showLoginFailure(String errorMessage);

        void showRegisterPage();

        void showChangeServerAddressUi();

        void showMoreUi();

        void showLoginButtonState(boolean enable);

        void resetLoginField();
    }

    interface Presenter extends BasePresenter{

        void login(String username,String password);

        void reset();

        void requestRegisterPage();

        void registerOnTextChangeListener(@NonNull EditText usernameEditText,
                                          @NonNull EditText passwordEditText);

        void displayChangeServerAddressUi();

        void displayMoreUi();

        void destroy();
    }
}
