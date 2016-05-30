package com.chendoing.gitcode.ui.activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chendoing.gitcode.GitCodeApplication;
import com.chendoing.gitcode.R;
import com.chendoing.gitcode.data.api.model.Event;
import com.chendoing.gitcode.data.api.model.User;
import com.chendoing.gitcode.injector.components.DaggerMainActivityComponent;
import com.chendoing.gitcode.presenters.MainActivityPresenter;
import com.chendoing.gitcode.presenters.views.MainView;
import com.chendoing.gitcode.ui.adapter.UserReceivedEventListAdapter;
import com.chendoing.gitcode.ui.view.RecyclerInsetsDecoration;
import com.jakewharton.rxbinding.view.RxView;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainView {

    @Inject
    MainActivityPresenter presenter;

    @BindView(R.id.layout_menu)
    DrawerLayout mMenu;
    @BindView(R.id.layout_menu_content)
    TextView mMenuTextView;

    @BindView(R.id.activity_main_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.activity_main_swiperefresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.activity_main_recycler)
    RecyclerView mRecyclerView;
    @BindView(R.id.activity_main_user_indicator)
    View mEmptyIndicator;
    @BindView(R.id.activity_main_error_view)
    View mErrorView;

    @BindView(R.id.view_login_thumb)
    RoundedImageView mUserThumb;
    @BindView(R.id.view_login_desc)
    TextView mUserName;

    UserReceivedEventListAdapter mUserReceivedEventListAdapter;

    private Snackbar mLoadingMoreEventIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
        initToolbar();
        initMenu();
        initRecyclerView();
        initDependencyInjector();
        initPresenter();
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void initMenu() {
        // Set the adapter for the list view
        mMenuTextView.setText("测试");
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onStart();
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new RecyclerInsetsDecoration(this));
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int visibleItemsCount = layoutManager.getChildCount();
                int totalItemsCount = layoutManager.getItemCount();
                int firstVisibleItemPos = layoutManager.findFirstVisibleItemPosition();

                if (visibleItemsCount + firstVisibleItemPos >= totalItemsCount) {
                    presenter.onEventsEndReached();
                }
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            mSwipeRefreshLayout.setRefreshing(true);
            presenter.refreshEvents();
        });
    }

    private void initToolbar() {
        mToolbar.setTitle(getString(R.string.activity_main_title));
    }

    private void initPresenter() {
        presenter.attachView(this);
    }

    private void initDependencyInjector() {
        DaggerMainActivityComponent.builder()
                .appComponent(((GitCodeApplication) getApplication()).getAppComponent())
                .build().inject(this);
    }

    private void initUI() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    public void showErrorView(String msg) {
        TextView textView = ButterKnife.findById(mErrorView, R.id.view_error_message);
        textView.setText(msg);
        mErrorView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideErrorView() {
        mErrorView.setVisibility(View.GONE);
    }

    @Override
    public void refreshEvents() {
        mUserReceivedEventListAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void bindEvents(List<Event> events) {
        mUserReceivedEventListAdapter = new UserReceivedEventListAdapter(
                this,
                events,
                this::showErrorView,
                this::showErrorView
        );
        mRecyclerView.setAdapter(mUserReceivedEventListAdapter);
    }

    @Override
    public void updateEvents(int eventsAdd) {
        mUserReceivedEventListAdapter.notifyItemChanged(
                mUserReceivedEventListAdapter.getItemCount() + eventsAdd, eventsAdd
        );
    }

    @Override
    public void onEventsEndReach() {
        mLoadingMoreEventIndicator = Snackbar.make(mRecyclerView, getString(R.string.no_more_event), Snackbar.LENGTH_LONG);
        mLoadingMoreEventIndicator.show();
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void showLoadingErrorView() {
        mLoadingMoreEventIndicator = Snackbar.make(mRecyclerView, getString(R.string.loading_event_error), Snackbar.LENGTH_LONG);
        mLoadingMoreEventIndicator.setAction("Try again", listener -> presenter.onErrorRetryRequest());
        mLoadingMoreEventIndicator.setActionTextColor(getColor(R.color.blue));
        mLoadingMoreEventIndicator.show();
    }


    @Override
    public void showIndicator(User user) {
        Glide.with(this)
                .load(user.getAvatar_url())
                .crossFade()
                .into(mUserThumb);
        mUserName.setText(getString(R.string.user_login_desc) + user.getLogin());
        mEmptyIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideIndicator() {
        mEmptyIndicator.setVisibility(View.GONE);
    }

    @Override
    public void showLoadingMoreEventIndicator() {
        mLoadingMoreEventIndicator = Snackbar.make(mRecyclerView, getString(R.string.loading_more_event), Snackbar.LENGTH_INDEFINITE);
        mLoadingMoreEventIndicator.show();
    }

    @Override
    public void hideLoadingMoreEventIndicator() {
        if (mLoadingMoreEventIndicator != null && mLoadingMoreEventIndicator.isShown()) {
            mLoadingMoreEventIndicator.dismiss();
        }
    }

    @Override
    public void onAuthFailed() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onNoEventError() {
        TextView textView = ButterKnife.findById(mErrorView, R.id.view_error_message);
        textView.setText(getString(R.string.loading_event_error));
        Button retry = ButterKnife.findById(mErrorView, R.id.view_error_retry_button);
        retry.setOnClickListener(listener -> presenter.onErrorRetryRequest());
        RxView.clicks(retry)
                .throttleFirst(3000L, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> {
                    presenter.onErrorRetryRequest();
                });
        mErrorView.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.view_error_retry_button)
    public void onRetryButtonClick(View v) {
        presenter.onErrorRetryRequest();
    }
}
