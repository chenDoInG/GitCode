package com.chendoing.gitcode.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.chendoing.gitcode.GitCodeApplication;
import com.chendoing.gitcode.R;
import com.chendoing.gitcode.data.api.GithubService;
import com.chendoing.gitcode.data.api.model.Token;
import com.chendoing.gitcode.injector.components.DaggerLoginActivityComponent;
import com.chendoing.gitcode.injector.modules.LoginActivityModule;
import com.chendoing.gitcode.injector.modules.ApiModule;
import com.chendoing.gitcode.presenters.LoginPresenter;
import com.chendoing.gitcode.presenters.views.LoginView;
import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.HttpUrl;

/**
 * Created by chenDoInG on 16/5/23.
 */
public class LoginActivity extends AppCompatActivity implements LoginView {

    @Inject
    LoginPresenter presenter;
    @Inject
    String authURI;

    @BindView(R.id.activity_login_webview)
    View mWebViewContainer;
    @BindView(R.id.button_personal)
    ImageView mPersonal;
    @BindView(R.id.button_enterprise)
    ImageView mEnterprise;


    private final static String REDIRECT_URI = "https://github.com/chenDoInG/CodeHub/callback";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
        initDependencyInjects();
        initPresenter();
        initPersonButtonListener();
    }

    private void initPresenter() {
        presenter.onCreate();
    }

    private void initDependencyInjects() {
        GitCodeApplication application = (GitCodeApplication) getApplication();
        DaggerLoginActivityComponent.builder()
                .appComponent(application.getAppComponent())
                .apiModule(new ApiModule(GithubService.TOKEN_URI))
                .loginActivityModule(new LoginActivityModule(this))
                .build().inject(this);
    }

    private void initUI() {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    private void initPersonButtonListener() {
        RxView.clicks(mPersonal)
                .throttleFirst(3000L, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> {
                    hideButtons();
                    showWebView();
                });

    }

    private void showWebView() {
        WebView webView = ButterKnife.findById(mWebViewContainer, R.id.webView);
        webView.loadUrl(authURI);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.contains(REDIRECT_URI)) {
                    String code = HttpUrl.parse(url).queryParameter("code");
                    presenter.getAccessToken(code);
                }
                return false;
            }
        });
        mWebViewContainer.setVisibility(View.VISIBLE);
    }

    public void hideButtons() {
        if (mPersonal.getVisibility() == View.VISIBLE) {
            mPersonal.setVisibility(View.GONE);
        }
        if (mEnterprise.getVisibility() == View.VISIBLE) {
            mEnterprise.setVisibility(View.GONE);
        }
    }

    public void showErrorMsg(String errMsg) {
        Toast.makeText(getApplicationContext(), errMsg, Toast.LENGTH_LONG).show();
    }

}
