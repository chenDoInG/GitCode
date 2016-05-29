package com.chendoing.gitcode.presenters;

import android.text.TextUtils;

import com.chendoing.gitcode.data.api.GithubResponse;
import com.chendoing.gitcode.data.api.model.User;
import com.chendoing.gitcode.injector.Activity;
import com.chendoing.gitcode.presenters.views.SplashView;
import com.chendoing.gitcode.presenters.views.View;
import com.f2prateek.rx.preferences.Preference;

import javax.inject.Inject;

/**
 * Created by chenDoInG on 16/5/29.
 */
@Activity
public class SplashActivityPresenter implements Presenter {

    private Preference<String> mToken;
    private SplashView mSplashView;
    private User mUser;
    private GithubResponse mGithubResponse;

    @Inject
    public SplashActivityPresenter(Preference<String> token, User user, GithubResponse response) {
        this.mToken = token;
        this.mUser = user;
        this.mGithubResponse = response;
    }

    @Override
    public void attachView(View view) {
        mSplashView = (SplashView) view;
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
        if (TextUtils.isEmpty(mToken.get())) {
            mSplashView.goToLoginActivity();
        } else {
            getUser();
        }
    }

    private void onUserReceived(User user) {
        mUser.setLogin(user.getLogin());
        mUser.setAvatar_url(user.getAvatar_url());
        mSplashView.goToMainActivity();
    }

    private void getUser() {
        mGithubResponse.getUser()
                .subscribe(this::onUserReceived);
    }
}
