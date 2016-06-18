package com.chendoing.gitcode.injector.modules;

import com.chendoing.gitcode.data.api.GithubResponse;
import com.chendoing.gitcode.injector.Activity;
import com.chendoing.gitcode.presenters.UserActivityPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class UserActivityModule {

    private String userName;

    public UserActivityModule(String userName) {
        this.userName = userName;
    }

    @Provides
    @Activity
    public UserActivityPresenter providesPresenter(GithubResponse response) {
        return new UserActivityPresenter(userName, response);
    }
}
