package com.chendoing.gitcode.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.chendoing.gitcode.Constant;
import com.chendoing.gitcode.GitCodeApplication;
import com.chendoing.gitcode.injector.components.DaggerSplashComponent;

import javax.inject.Inject;

public class SplashActivity extends AppCompatActivity {

    @Inject
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GitCodeApplication gitCodeApplication = (GitCodeApplication) getApplication();
        DaggerSplashComponent.builder().appComponent(gitCodeApplication.getAppComponent())
                .build().inject(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent;
        if (TextUtils.isEmpty(sp.getString(Constant.USER_TOKEN, null))) {
            intent = new Intent(this, LoginActivity.class);
        } else {
            intent = new Intent(this, MainActivity.class);
        }
        startActivity(intent);
        finish();
    }
}