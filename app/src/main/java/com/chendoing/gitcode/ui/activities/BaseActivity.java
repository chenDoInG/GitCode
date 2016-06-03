package com.chendoing.gitcode.ui.activities;

import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chendoing.gitcode.R;
import com.chendoing.gitcode.data.api.model.User;
import com.makeramen.roundedimageview.RoundedImageView;

import org.w3c.dom.Text;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenDoInG on 16/5/31.
 */
public abstract class BaseActivity extends AppCompatActivity {


    @Inject
    User mUser;

    DrawerLayout mDrawerLayout;
    FrameLayout mContainer;

    @BindView(R.id.activity_base_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.nav_head_thumb)
    RoundedImageView mNavHeadThumb;
    @BindView(R.id.nav_head_username)
    TextView mNavHeadUserName;
    @BindView(R.id.menu_personal)
    RelativeLayout mMenuPersonal;
    @BindView(R.id.icon_user)
    RoundedImageView mMenuIconUser;
    @BindView(R.id.menu_username)
    TextView mMenuUserName;


    @Override
    protected void onStart() {
        super.onStart();
        bindView();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        mDrawerLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        mContainer = (FrameLayout) mDrawerLayout.findViewById(R.id.container);
        getLayoutInflater().inflate(layoutResID, mContainer, true);
        super.setContentView(mDrawerLayout);
        ButterKnife.bind(this);
        initToolbar();
        initMenu();
    }

    private void bindView() {
        Glide.with(this)
                .load(mUser.getAvatar_url())
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(mNavHeadThumb);
        Glide.with(this)
                .load(mUser.getAvatar_url())
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(mMenuIconUser);
        mNavHeadUserName.setText(mUser.getLogin());
        mMenuUserName.setText(mUser.getLogin());

    }

    private void initMenu() {
        mMenuPersonal.setOnClickListener(l -> goToOtherActivity(UserActivity.class));
    }

    private void goToOtherActivity(Class<? extends BaseActivity> activity) {
        if (this.getClass() != activity) {
            Intent intent = new Intent(this, activity);
            startActivity(intent);
            finish();
        }
    }

    private void initToolbar() {
        mToolbar.setTitle(getString(R.string.activity_main_title));
        mToolbar.setNavigationIcon(R.drawable.three_lines);
        mToolbar.setNavigationOnClickListener(listener -> {
            mDrawerLayout.openDrawer(GravityCompat.START);
            mToolbar.setNavigationIcon(R.drawable.back);
        });
    }

}
