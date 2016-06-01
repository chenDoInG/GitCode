package com.chendoing.gitcode.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.chendoing.gitcode.GitCodeApplication;
import com.chendoing.gitcode.R;
import com.chendoing.gitcode.injector.components.DaggerSplashComponent;
import com.chendoing.gitcode.presenters.SplashActivityPresenter;
import com.chendoing.gitcode.presenters.views.SplashView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity implements SplashView {

    @Inject
    SplashActivityPresenter mSplashActivityPresenter;

    @BindView(R.id.activity_splash_layout)
    LinearLayout mLinearLayout;

    private Snackbar mSnackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
        initDependencyInjector();
        initPresenter();
    }

    private void initUI() {
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mSplashActivityPresenter.onStart();
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
        startActivity(new Intent(this, NewsActivity.class));
    }

    @Override
    public void goToLoginActivity() {
        startActivity(new Intent(this, LoginActivity.class));
    }

    @Override
    public void showNetworkErrorView() {
        mSnackbar = Snackbar.make(mLinearLayout,"network error",Snackbar.LENGTH_INDEFINITE);
        mSnackbar.show();
    }
}