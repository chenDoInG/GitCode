package com.chendoing.gitcode.presenters;

import android.text.TextUtils;

import com.chendoing.gitcode.BuildConfig;
import com.chendoing.gitcode.data.api.GithubResponse;
import com.chendoing.gitcode.data.api.model.User;
import com.chendoing.gitcode.injector.Activity;
import com.chendoing.gitcode.presenters.views.SplashView;
import com.chendoing.gitcode.presenters.views.View;
import com.f2prateek.rx.preferences.Preference;

import javax.inject.Inject;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.HttpException;
import timber.log.Timber;

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
        if (TextUtils.isEmpty(mToken.get())) {
            mSplashView.goToLoginActivity();
        } else {
            getUser();
        }
    }

    @Override
    public void onStop() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onCreate() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        mSplashView.showNetworkErrorView();
    }

    private void onUserReceived(User user) {
        mUser.copy(user);
        mSplashView.goToMainActivity();
    }

    private void onLoginUserError(Throwable throwable) {
        Timber.e(throwable.toString());
    }

    private void getUser() {
        mGithubResponse.getUser()
                .subscribe(this::onUserReceived, this::onLoginUserError);
    }
}
