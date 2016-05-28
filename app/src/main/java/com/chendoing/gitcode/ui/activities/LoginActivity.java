package com.chendoing.gitcode.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.chendoing.gitcode.GitCodeApplication;
import com.chendoing.gitcode.R;
import com.chendoing.gitcode.data.api.GithubResponse;
import com.chendoing.gitcode.injector.components.DaggerLoginActivityComponent;
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


    @BindView(R.id.activity_login_webview)
    View mWebViewContainer;
    @BindView(R.id.activity_login_error_view)
    View mErrorView;
    @BindView(R.id.activity_login_empty_indicator)
    View mIndicator;

    @BindView(R.id.button_personal)
    ImageView mPersonal;
    @BindView(R.id.button_enterprise)
    ImageView mEnterprise;

    @BindView(R.id.activity_login_toolbar)
    Toolbar mToolbar;

    private Snackbar mLoadingSnack;


    private final static String REDIRECT_URI = "https://github.com/chenDoInG/CodeHub/callback";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
        initToolbar();
        initDependencyInjects();
        initPresenter();
        initPersonButtonListener();
    }

    private void initPresenter() {
        presenter.attachView(this);
        presenter.onCreate();
    }

    private void initDependencyInjects() {
        GitCodeApplication application = (GitCodeApplication) getApplication();
        DaggerLoginActivityComponent.builder()
                .appComponent(application.getAppComponent())
                .build().inject(this);
    }

    private void initToolbar() {
        mToolbar.setTitle(getString(R.string.activity_login_title));
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

    @Override
    public void showLoadingIndicator() {
        mLoadingSnack = Snackbar.make(mWebViewContainer,
                getString(R.string.message_loading), Snackbar.LENGTH_INDEFINITE);
        mLoadingSnack.show();
    }

    @Override
    public void hideLoadingIndicator() {
        if (mLoadingSnack != null) mLoadingSnack.dismiss();
    }

    @Override
    public void showLoadingView() {
        mIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingView() {
        if (mIndicator.getVisibility() == View.VISIBLE) {
            mIndicator.setVisibility(View.GONE);
        }
    }

    private void showWebView() {
        showLoadingView();
        WebView webView = ButterKnife.findById(mWebViewContainer, R.id.webView);
        webView.loadUrl(GithubResponse.AUTH_URI);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                hideLoadingView();
                if (url.contains(REDIRECT_URI)) {
                    String code = HttpUrl.parse(url).queryParameter("code");
                    System.out.println(code);
                    presenter.getAccessToken(code);
                }
                return false;
            }
        });
        mWebViewContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideButtons() {
        if (mPersonal.getVisibility() == View.VISIBLE) {
            mPersonal.setVisibility(View.GONE);
        }
        if (mEnterprise.getVisibility() == View.VISIBLE) {
            mEnterprise.setVisibility(View.GONE);
        }
    }

    @Override
    public void showErrorView(String errMsg) {
        TextView errorTextView = ButterKnife.findById(mErrorView, R.id.view_error_message);
        errorTextView.setText(errMsg);
        mErrorView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideErrorView() {
        if (mErrorView.getVisibility() == View.VISIBLE) {
            mErrorView.setVisibility(View.GONE);
        }
    }

    @Override
    public void goToMainView() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
