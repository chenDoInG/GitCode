package com.chendoing.gitcode.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chendoing.gitcode.R;
import com.jakewharton.rxbinding.view.RxView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenDoInG on 16/5/23.
 */
public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.edit_username)
    EditText mEditUsername;
    @BindView(R.id.edit_pwd)
    EditText mEditPassword;
    @BindView(R.id.button_login)
    Button mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
        initLoginButton();
    }

    private void initUI() {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    private void initLoginButton() {
        RxView.clicks(mLoginButton)
                .takeFirst(predicate-> !TextUtils.isEmpty(mEditUsername.getText()))
                .subscribe(aVoid -> Toast.makeText(getApplicationContext(), mEditUsername.getText(), Toast.LENGTH_SHORT).show());
    }

}
