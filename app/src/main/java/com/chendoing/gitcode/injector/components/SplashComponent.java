package com.chendoing.gitcode.injector.components;

import com.chendoing.gitcode.injector.Activity;
import com.chendoing.gitcode.ui.activities.SplashActivity;

import dagger.Component;

@Activity@Component(dependencies = AppComponent.class)
public interface SplashComponent {

    void inject(SplashActivity splashActivity);
}
