package com.chendoing.gitcode.presenters;

import com.chendoing.gitcode.data.api.GithubResponse;
import com.chendoing.gitcode.data.api.model.Event;
import com.chendoing.gitcode.injector.Activity;
import com.chendoing.gitcode.presenters.views.MainView;
import com.chendoing.gitcode.presenters.views.View;
import com.f2prateek.rx.preferences.Preference;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by chenDoInG on 16/5/25.
 */
@Activity
public class MainActivityPresenter implements Presenter {

    private MainView mainView;

    private GithubResponse response;

    private boolean mIsEventRequestRunning;

    private Preference<String> token;

    @Inject
    public MainActivityPresenter(GithubResponse reponse, Preference<String> token) {
        this.response = reponse;
        this.token = token;
    }

    public void askForNewFeed() {
        mIsEventRequestRunning = true;
        mainView.showIndicator();

        response.getUser()
                .flatMap(user -> response.getUserReceivedEvents(user.getName()))
                .subscribe(this::onEventReceived, this::onAuthFailed);

    }

    private void onAuthFailed(Throwable throwable) {
        token.delete();
        mainView.onAuthFailed();
    }

    private void onEventReceived(List<Event> events) {
        mIsEventRequestRunning = false;
        mainView.bindEventList(events);
        mainView.hideIndicator();
        mainView.showEventListView();
    }

    private void showErrorMsg(Event msg) {
        mainView.showErrorView(msg.toString());
    }

    @Override
    public void attachView(View view) {
        mainView = (MainView) view;
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
        askForNewFeed();
    }
}
