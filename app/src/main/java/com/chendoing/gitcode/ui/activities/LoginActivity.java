package com.chendoing.gitcode.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.chendoing.gitcode.GitCodeApplication;
import com.chendoing.gitcode.R;
import com.chendoing.gitcode.injector.components.DaggerLoginActivityComponent;
import com.chendoing.gitcode.injector.modules.LoginActivityModule;
import com.chendoing.gitcode.presenters.LoginPresenter;
import com.chendoing.gitcode.presenters.views.LoginView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenDoInG on 16/5/23.
 */
public class LoginActivity extends AppCompatActivity implements LoginView {

    @Inject
    LoginPresenter presenter;

    @BindView(R.id.webView)
    WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
        initDependencyInjects();
        initPresenter();
        initWebView();
    }

    private void initPresenter() {
        presenter.onCreate();
    }

    private void initDependencyInjects() {
        GitCodeApplication application = (GitCodeApplication) getApplication();
        DaggerLoginActivityComponent.builder()
                .appComponent(application.getAppComponent())
                .loginActivityModule(new LoginActivityModule(this))
                .build().inject(this);
    }

    private void initUI() {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    private void initWebView() {
        mWebView.loadUrl("https://github.com/login/oauth/authorize?client_id=");
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
    }

}
