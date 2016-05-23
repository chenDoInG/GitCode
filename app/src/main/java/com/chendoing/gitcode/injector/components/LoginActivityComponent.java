package com.chendoing.gitcode.injector.components;

import com.chendoing.gitcode.injector.Activity;
import com.chendoing.gitcode.injector.modules.LoginActivityModule;
import com.chendoing.gitcode.injector.modules.NetModule;
import com.chendoing.gitcode.presenters.LoginPresenter;
import com.chendoing.gitcode.ui.activities.LoginActivity;

import dagger.Component;

@Activity
@Component(dependencies = AppComponent.class, modules = {NetModule.class, LoginActivityModule.class})
public interface LoginActivityComponent {

    void inject(LoginActivity loginActivity);

    LoginPresenter presenter();
}
