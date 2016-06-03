package com.chendoing.gitcode.presenters;

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

    private User mUser;

    private UserView mView;

    @Inject
    public UserActivityPresenter(User user) {
        this.mUser = user;
    }

    @Override
    public void attachView(View view) {
        mView = (UserView) view;
    }

    @Override
    public void onStart() {
        mView.bindView(mUser);
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
