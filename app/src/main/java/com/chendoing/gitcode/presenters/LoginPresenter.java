package com.chendoing.gitcode.presenters;

import com.chendoing.gitcode.presenters.views.LoginView;

/**
 * Created by chenDoInG on 16/5/23.
 */
public class LoginPresenter implements LoginView,Presenter{

    private LoginView view;

    public LoginPresenter(LoginView view){
        this.view = view;
    }

    @Override
    public void onStart() {

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
