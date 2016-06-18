package com.chendoing.gitcode.presenters;

import com.chendoing.gitcode.data.api.GithubResponse;
import com.chendoing.gitcode.data.api.model.User;
import com.chendoing.gitcode.injector.Activity;
import com.chendoing.gitcode.presenters.views.UserView;
import com.chendoing.gitcode.presenters.views.View;

import javax.inject.Inject;

/**
 * Created by chenDoInG on 16/6/2.
 */
@Activity
public class UserActivityPresenter implements Presenter {

    private String mUser;

    private GithubResponse mGithubResponse;

    private UserView mView;


    public UserActivityPresenter(String userName, GithubResponse response) {
        this.mUser = userName;
        this.mGithubResponse = response;
    }

    @Override
    public void attachView(View view) {
        mView = (UserView) view;
    }

    @Override
    public void onStart() {
        mGithubResponse.getUser(mUser)
                .subscribe(mView::bindView);
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
