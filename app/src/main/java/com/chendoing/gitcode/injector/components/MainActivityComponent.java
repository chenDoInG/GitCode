package com.chendoing.gitcode.injector.components;

import com.chendoing.gitcode.injector.Activity;
import com.chendoing.gitcode.ui.activities.NewsActivity;

import dagger.Component;

@Activity
@Component(dependencies = AppComponent.class)
public interface MainActivityComponent {

    void inject(NewsActivity mainActivity);
}
