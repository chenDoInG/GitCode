package com.chendoing.gitcode.presenters;

import com.chendoing.gitcode.data.api.GithubResponse;
import com.chendoing.gitcode.injector.Activity;
import com.chendoing.gitcode.presenters.views.RepositoryView;
import com.chendoing.gitcode.presenters.views.View;

@Activity
public class RepositoryActivityPresenter implements Presenter {

    private RepositoryView mRepositoryView;

    private GithubResponse mResponse;

    private String userName;

    private String repo;

    public RepositoryActivityPresenter(String userName,String repo,GithubResponse response) {
        mResponse = response;
        this.userName = userName;
        this.repo = repo;
    }

    @Override
    public void attachView(View view) {
        mRepositoryView = (RepositoryView) view;
    }

    @Override
    public void onStart() {
        mResponse.getUserRepository(userName,repo)
                .subscribe(mRepositoryView::bindView);
    }

    @Override
    public void onStop() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onCreate() {

    }
}
