package com.chendoing.gitcode.injector.modules;

import com.chendoing.gitcode.injector.Activity;
import com.chendoing.gitcode.presenters.LoginPresenter;
import com.chendoing.gitcode.presenters.views.LoginView;
import com.chendoing.gitcode.ui.activities.LoginActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class LoginActivityModule {

    private LoginView view;

    public LoginActivityModule(LoginActivity loginActivity) {
        this.view = loginActivity;
    }

    @Provides
    @Activity
    public LoginPresenter providesLoginPresenter() {
        return new LoginPresenter(view);
    }
}
