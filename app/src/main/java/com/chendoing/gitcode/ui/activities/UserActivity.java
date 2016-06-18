package com.chendoing.gitcode.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chendoing.gitcode.GitCodeApplication;
import com.chendoing.gitcode.R;
import com.chendoing.gitcode.data.api.model.User;
import com.chendoing.gitcode.injector.components.DaggerUserActivityComponent;
import com.chendoing.gitcode.injector.modules.UserActivityModule;
import com.chendoing.gitcode.presenters.UserActivityPresenter;
import com.chendoing.gitcode.presenters.views.UserView;
import com.makeramen.roundedimageview.RoundedImageView;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by chenDoInG on 16/6/1.
 */
public class UserActivity extends BaseActivity implements UserView {

    private final static String USER_NAME = "userActivity_user_name";
    @Inject
    UserActivityPresenter mPresenter;

    @BindView(R.id.activity_user_thumb)
    RoundedImageView mRoundedImageView;
    @BindView(R.id.activity_user_name)
    TextView mTextView;

    @BindView(R.id.activity_user_follower)
    TextView mFollower;
    @BindView(R.id.activity_user_following)
    TextView mFollowing;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
        initDependencyInjector();
        initPresenter();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.onStart();
    }

    private void initPresenter() {
        mPresenter.attachView(this);
    }

    private void initDependencyInjector() {
        DaggerUserActivityComponent.builder()
                .appComponent(((GitCodeApplication) getApplication()).getAppComponent())
                .userActivityModule(new UserActivityModule(getIntent().getStringExtra(USER_NAME)))
                .build().inject(this);
    }

    private void initUI() {
        setContentView(R.layout.activity_user);
        mToolbar.setTitle("");
    }

    @Override
    public void bindView(User user) {
        Glide.with(this)
                .load(user.getAvatar_url())
                .crossFade()
                .into(mRoundedImageView);
        mTextView.setText(user.getLogin());
        mFollower.setText(String.valueOf(user.getFollowers()));
        mFollowing.setText(String.valueOf(user.getFollowing()));
    }

    public static void gotoUserActivity(Context context, String userName) {
        Intent intent = new Intent(context, UserActivity.class);
        intent.putExtra(USER_NAME, userName);
        context.startActivity(intent);
    }
}
