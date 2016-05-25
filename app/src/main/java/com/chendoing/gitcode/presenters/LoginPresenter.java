package com.chendoing.gitcode.presenters;

import com.chendoing.gitcode.data.api.GithubResponse;
import com.chendoing.gitcode.data.api.model.Token;
import com.chendoing.gitcode.presenters.views.LoginView;
import com.chendoing.gitcode.presenters.views.View;
import com.f2prateek.rx.preferences.Preference;

import javax.inject.Inject;

/**
 * Created by chenDoInG on 16/5/23.
 */
public class LoginPresenter implements Presenter {

    private LoginView loginView;

    private GithubResponse reponse;

    private final Preference<String> accessToken;

    @Inject
    public LoginPresenter(GithubResponse reponse, Preference<String> accessToken) {
        this.reponse = reponse;
        this.accessToken = accessToken;
    }

    public void getAccessToken(String code) {
        loginView.showLoadingIndicator();
        reponse.getUserToken(code)
                .subscribe(this::storeToken, this::showErrorMsg);
    }

    private void storeToken(Token token) {
        accessToken.set(token.getAccessToken());
        loginView.hideLoadingIndicator();
        loginView.goToMainView();
    }

    private void showErrorMsg(Throwable throwable) {
        loginView.showErrorView(throwable.getMessage());
    }

    @Override
    public void attachView(View view) {
        this.loginView = (LoginView) view;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onCreate() {

    }

}
