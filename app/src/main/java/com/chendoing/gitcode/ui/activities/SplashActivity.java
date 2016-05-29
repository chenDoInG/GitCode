package com.chendoing.gitcode.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.chendoing.gitcode.GitCodeApplication;
import com.chendoing.gitcode.injector.components.DaggerSplashComponent;
import com.chendoing.gitcode.presenters.SplashActivityPresenter;
import com.chendoing.gitcode.presenters.views.SplashView;

import javax.inject.Inject;

public class SplashActivity extends AppCompatActivity implements SplashView {

    @Inject
    SplashActivityPresenter mSplashActivityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDependencyInjector();
        initPresenter();
    }

    private void initPresenter() {
        mSplashActivityPresenter.attachView(this);
        mSplashActivityPresenter.onCreate();
    }

    private void initDependencyInjector() {
        GitCodeApplication gitCodeApplication = (GitCodeApplication) getApplication();
        DaggerSplashComponent.builder().appComponent(gitCodeApplication.getAppComponent())
                .build().inject(this);
    }

    @Override
    public void goToMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void goToLoginActivity() {
        startActivity(new Intent(this, LoginActivity.class));
    }
}