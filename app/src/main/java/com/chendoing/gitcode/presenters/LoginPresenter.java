package com.chendoing.gitcode.presenters;

import android.text.TextUtils;

import com.chendoing.gitcode.data.api.GithubResponse;
import com.chendoing.gitcode.data.api.model.Token;
import com.chendoing.gitcode.data.api.model.User;
import com.chendoing.gitcode.injector.Activity;
import com.chendoing.gitcode.presenters.views.LoginView;
import com.chendoing.gitcode.presenters.views.View;
import com.f2prateek.rx.preferences.BuildConfig;
import com.f2prateek.rx.preferences.Preference;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by chenDoInG on 16/5/23.
 */
@Activity
public class LoginPresenter implements Presenter {

    private LoginView loginView;

    private GithubResponse reponse;

    private final Preference<String> accessToken;
    private User mUser;

    @Inject
    public LoginPresenter(GithubResponse reponse, Preference<String> accessToken, User user) {
        this.reponse = reponse;
        this.accessToken = accessToken;
        this.mUser = user;
    }

    public void getAccessToken(String code) {
        loginView.showLoadingIndicator();
        reponse.getUserToken(code)
                .subscribe(this::storeToken, this::showErrorMsg);
        getUser();
    }

    private void getUser() {
        reponse.getUser()
                .subscribe(this::storeUser, this::showErrorMsg);
    }

    private void storeUser(User user) {
        loginView.hideWebView();
        mUser.setLogin(user.getLogin());
        mUser.setAvatar_url(user.getAvatar_url());
        loginView.goToMainView();
    }

    private void storeToken(Token token) {
        accessToken.set(token.getAccess_token());
        loginView.hideLoadingIndicator();
    }

    private void showErrorMsg(Throwable throwable) {
        Timber.e(throwable.getMessage());
        loginView.hideWebView();
        loginView.showErrorView(throwable.getMessage());
    }

    @Override
    public void attachView(View view) {
        this.loginView = (LoginView) view;
    }

    @Override
    public void onStart() {
        if (!TextUtils.isEmpty(accessToken.get()))
            getUser();
    }

    @Override
    public void onStop() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onCreate() {
        if(BuildConfig.DEBUG){
            Timber.plant(new Timber.DebugTree());
        }
    }

}
