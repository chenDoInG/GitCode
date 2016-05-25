package com.chendoing.gitcode.injector.components;

import com.chendoing.gitcode.injector.Activity;
import com.chendoing.gitcode.ui.activities.LoginActivity;

import dagger.Component;

@Activity
@Component(dependencies = AppComponent.class)
public interface LoginActivityComponent {

    void inject(LoginActivity loginActivity);

}
