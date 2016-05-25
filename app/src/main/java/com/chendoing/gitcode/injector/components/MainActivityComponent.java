package com.chendoing.gitcode.injector.components;

import com.chendoing.gitcode.injector.Activity;
import com.chendoing.gitcode.ui.activities.MainActivity;

import dagger.Component;

@Activity
@Component(dependencies = AppComponent.class)
public interface MainActivityComponent {

    void inject(MainActivity mainActivity);
}
