package com.chendoing.gitcode.injector.components;

import com.chendoing.gitcode.injector.Activity;
import com.chendoing.gitcode.ui.activities.UserActivity;

import dagger.Component;

/**
 * Created by chenDoInG on 16/6/2.
 */
@Activity
@Component(dependencies = AppComponent.class)
public interface UserActivityComponent {

    void inject(UserActivity activity);
}
