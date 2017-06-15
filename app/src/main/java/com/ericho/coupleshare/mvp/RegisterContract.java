package com.ericho.coupleshare.mvp;

import android.support.annotation.NonNull;
import android.widget.EditText;

/**
 * Created by steve_000 on 14/6/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.mvp
 */

public interface RegisterContract {
    interface View extends BaseView<Presenter>{

        void showLoadingIndicator(boolean active);

        void showRegisterSuccess();

        void showRegisterFailure(String errorMessage);

        void showRegisterButtonState(boolean enable);

    }

    interface Presenter extends BasePresenter{

        void register(String username,String password);

        void reset();

        void assignOnTextChangeListener(@NonNull EditText usernameEditText,
                                          @NonNull EditText passwordEditText);

        void destroy();
    }
}
