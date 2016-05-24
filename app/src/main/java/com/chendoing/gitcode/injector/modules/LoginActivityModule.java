package com.chendoing.gitcode.injector.modules;

import com.chendoing.gitcode.data.api.GithubReponse;
import com.chendoing.gitcode.data.api.GithubService;
import com.chendoing.gitcode.data.api.oauth.AccessToken;
import com.chendoing.gitcode.injector.Activity;
import com.chendoing.gitcode.presenters.LoginPresenter;
import com.chendoing.gitcode.presenters.views.LoginView;
import com.chendoing.gitcode.ui.activities.LoginActivity;
import com.f2prateek.rx.preferences.Preference;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import rx.Scheduler;

@Module
public class LoginActivityModule {

    private LoginView view;

    public LoginActivityModule(LoginActivity loginActivity) {
        this.view = loginActivity;
    }

    @Provides
    @Activity
    String providesAuthURI() {
        return GithubReponse.AUTH_URI;
    }

    @Provides
    @Activity
    public GithubReponse providesAccessToken(Retrofit retrofit, @Named("ui_thread") Scheduler uiThread, @Named("executor_thread") Scheduler executorThread) {
        return new GithubReponse(uiThread, executorThread, retrofit.create(GithubService.class));
    }

    @Provides
    public LoginPresenter providesLoginPresenter(GithubReponse reponse,Preference<String> token) {
        return new LoginPresenter(view, reponse, token);
    }
}
