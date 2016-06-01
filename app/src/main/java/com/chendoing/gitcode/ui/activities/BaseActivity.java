package com.chendoing.gitcode.ui.activities;

import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.chendoing.gitcode.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenDoInG on 16/5/31.
 */
public class BaseActivity extends AppCompatActivity {


    DrawerLayout mDrawerLayout;
    FrameLayout mContainer;

    @BindView(R.id.activity_base_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.nav_view)
    NavigationView mNavigationView;

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

    private void initMenu() {
        mNavigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_explore_fragment:
                    break;
                case R.id.nav_news_fragment:
                    break;
            }
            return true;
        });
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

    private void initUI() {
        setContentView(R.layout.activity_base);
    }
}
