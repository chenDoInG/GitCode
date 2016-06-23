package com.chendoing.gitcode.ui.activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chendoing.gitcode.GitCodeApplication;
import com.chendoing.gitcode.R;
import com.chendoing.gitcode.data.api.GithubResponse;
import com.chendoing.gitcode.data.api.model.Event;
import com.chendoing.gitcode.data.api.model.Repository;
import com.chendoing.gitcode.data.api.model.User;
import com.chendoing.gitcode.injector.components.DaggerMainActivityComponent;
import com.chendoing.gitcode.presenters.NewsActivityPresenter;
import com.chendoing.gitcode.presenters.views.NewsView;
import com.chendoing.gitcode.ui.adapter.UserReceivedEventListAdapter;
import com.chendoing.gitcode.ui.view.RecyclerInsetsDecoration;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewsActivity extends BaseActivity implements NewsView {

    @Inject
    NewsActivityPresenter presenter;
    @Inject
    GithubResponse mGithubResponse;

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
        initRecyclerView();
        initDependencyInjector();
        initPresenter();
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

    private void initPresenter() {
        presenter.attachView(this);
    }

    private void initDependencyInjector() {
        DaggerMainActivityComponent.builder()
                .appComponent(((GitCodeApplication) getApplication()).getAppComponent())
                .build().inject(this);
    }

    private void initUI() {
        setContentView(R.layout.activity_news);
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
                this::goToUserActivity,
                this::goToRepositoryActivity,
                mGithubResponse
        );
        mRecyclerView.setAdapter(mUserReceivedEventListAdapter);
    }


    private void goToUserActivity(String userName) {
        UserActivity.gotoUserActivity(this, userName);
    }

    private void goToRepositoryActivity(String userName, String repo) {
        RepositoryActivity.goToRepositoryActivity(this, userName, repo);
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

    @BindView(R.id.view_error_retry_button)
    Button mButton;

    @Override
    public void onNoEventError() {
        TextView textView = ButterKnife.findById(mErrorView, R.id.view_error_message);
        textView.setText(getString(R.string.loading_event_error));
//        Button retry = ButterKnife.findById(mErrorView, R.id.view_error_retry_button);
//        retry.setOnClickListener(listener -> presenter.onErrorRetryRequest());
//        RxView.clicks(retry)
//                .throttleFirst(3000L, TimeUnit.MILLISECONDS)
//                .subscribe(aVoid -> {
//                    presenter.onErrorRetryRequest();
//                });
        mButton.setOnClickListener(listener -> presenter.onErrorRetryRequest());
        mErrorView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEventView() {
        if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.getVisibility() == View.GONE)
            mSwipeRefreshLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEventView() {
        mSwipeRefreshLayout.setVisibility(View.GONE);
    }

    @OnClick(R.id.view_error_retry_button)
    public void onRetryButtonClick(View v) {
        presenter.onErrorRetryRequest();
    }
}
