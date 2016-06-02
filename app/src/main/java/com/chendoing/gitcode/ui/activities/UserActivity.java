package com.chendoing.gitcode.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.chendoing.gitcode.R;

/**
 * Created by chenDoInG on 16/6/1.
 */
public class UserActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
    }

    private void initUI() {
        setContentView(R.layout.activity_user);
        mToolbar.setTitle("");
    }

    @Override
    protected int getUsingMenuItemId() {
        return R.id.nav_profile_fragment;
    }
}
