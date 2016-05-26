package com.chendoing.gitcode.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chendoing.gitcode.GitCodeApplication;
import com.chendoing.gitcode.R;
import com.chendoing.gitcode.data.api.model.events.Event;
import com.chendoing.gitcode.injector.components.DaggerMainActivityComponent;
import com.chendoing.gitcode.presenters.MainActivityPresenter;
import com.chendoing.gitcode.presenters.views.MainView;
import com.chendoing.gitcode.ui.adapter.UserReceivedEventListAdapter;
import com.chendoing.gitcode.ui.view.RecyclerInsetsDecoration;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainView {

    @Inject
    MainActivityPresenter presenter;

    @BindView(R.id.activity_main_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.activity_main_recycler)
    RecyclerView mRecyclerView;
    @BindView(R.id.activity_main_empty_indicator)
    View mEmptyIndicator;

    UserReceivedEventListAdapter mUserReceivedEventListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
        initToolbar();
        initRecyclerView();
        initDependencyInjector();
        initPresenter();
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new RecyclerInsetsDecoration(this));
    }

    private void initToolbar() {
        mToolbar.setTitle(getString(R.string.activity_main_title));
    }

    private void initPresenter() {
        presenter.attachView(this);
        presenter.onCreate();
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
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void bindEventList(List<Event> events) {
        mUserReceivedEventListAdapter = new UserReceivedEventListAdapter(this,events, ((position, sharedView) -> {
            showErrorView(((TextView) sharedView).getText().toString());
        }));
        mRecyclerView.setAdapter(mUserReceivedEventListAdapter);
    }

    @Override
    public void showEventListView() {
        if (mRecyclerView.getVisibility() != View.VISIBLE) {
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideEventListView() {

    }

    @Override
    public void showIndicator() {
        if (mEmptyIndicator.getVisibility() != View.VISIBLE) {
            mEmptyIndicator.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideIndicator() {
        mEmptyIndicator.setVisibility(View.GONE);
    }
}
