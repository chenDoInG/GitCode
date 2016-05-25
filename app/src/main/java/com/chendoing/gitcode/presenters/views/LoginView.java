package com.chendoing.gitcode.presenters.views;

/**
 * Created by chenDoInG on 16/5/23.
 */
public interface LoginView {

    void hideButtons();

    void showErrorView(String errMsg);
    void hideErrorView();

    void goToMainView();

    void showLoadingIndicator();

    void hideLoadingIndicator();

    void showLoadingView();
    void hideLoadingView();
}
