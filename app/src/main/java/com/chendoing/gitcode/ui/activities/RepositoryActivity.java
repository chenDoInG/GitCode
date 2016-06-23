package com.chendoing.gitcode.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chendoing.gitcode.GitCodeApplication;
import com.chendoing.gitcode.R;
import com.chendoing.gitcode.data.api.model.Repository;
import com.chendoing.gitcode.injector.components.DaggerRepositoryActivityComponent;
import com.chendoing.gitcode.injector.modules.RepositoryActivityModule;
import com.chendoing.gitcode.presenters.RepositoryActivityPresenter;
import com.chendoing.gitcode.presenters.views.RepositoryView;
import com.makeramen.roundedimageview.RoundedImageView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenDoInG on 16/6/23.
 */
public class RepositoryActivity extends BaseActivity implements RepositoryView {

    private final static String USER_NAME = "repositoryactivity_user_name";
    private final static String REPO_NAME = "repositoryactivity_repo_name";
    @Inject
    RepositoryActivityPresenter mPresenter;

    @BindView(R.id.activity_repository_thumb)
    RoundedImageView mUserThumb;
    @BindView(R.id.activity_repository_name)
    TextView mUserName;
    @BindView(R.id.activity_repository_forks)
    TextView mRepoFork;
    @BindView(R.id.activity_repository_stars)
    TextView mRepoStar;
    @BindView(R.id.activity_repository_watchs)
    TextView mRepoWatch;

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
        DaggerRepositoryActivityComponent.builder()
                .appComponent(((GitCodeApplication) getApplication()).getAppComponent())
                .repositoryActivityModule(new RepositoryActivityModule(
                        getIntent().getStringExtra(USER_NAME),
                        getIntent().getStringExtra(REPO_NAME)))
                .build()
                .inject(this);
    }

    private void initUI() {
        setContentView(R.layout.activity_repository);
        mToolbar.setTitle("");
    }

    public static void goToRepositoryActivity(Context context, String userName, String repo) {
        Intent intent = new Intent(context, RepositoryActivity.class);
        intent.putExtra(USER_NAME, userName);
        intent.putExtra(REPO_NAME, repo);
        context.startActivity(intent);
    }

    @Override
    public void bindView(Repository repository) {
        Glide.with(this)
                .load(repository.getOwner().getAvatar_url())
                .crossFade()
                .into(mUserThumb);
        mUserName.setText(repository.getName());
        mRepoFork.setText(String.valueOf(repository.getForks_count()));
        mRepoStar.setText(String.valueOf(repository.getStargazers_count()));
        mRepoWatch.setText(String.valueOf(repository.getWatchers_count()));
    }
}
