package com.chendoing.gitcode.presenters;

import com.chendoing.gitcode.data.api.GithubResponse;
import com.chendoing.gitcode.data.api.model.Event;
import com.chendoing.gitcode.data.api.model.User;
import com.chendoing.gitcode.injector.Activity;
import com.chendoing.gitcode.presenters.views.MainView;
import com.chendoing.gitcode.presenters.views.View;
import com.f2prateek.rx.preferences.Preference;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by chenDoInG on 16/5/25.
 */
@Activity
public class MainActivityPresenter implements Presenter {

    private MainView mainView;

    private GithubResponse response;

    private boolean mIsEventRequestRunning;

    private Preference<String> token;

    private int page;

    private Subscription mSubscription;

    private List<Event> mEvents = new ArrayList<>();

    private User mUser;

    @Inject
    public MainActivityPresenter(GithubResponse reponse, Preference<String> token, User user) {
        this.response = reponse;
        this.token = token;
        page = 1;
        this.mUser = user;
    }

    public void askForEvent() {
        mIsEventRequestRunning = true;
        mainView.hideErrorView();
        mainView.showIndicator(mUser);
        mSubscription = response.getUser()
                .flatMap(user -> response.getUserReceivedEvents(user.getName(), 1))
                .subscribe(this::onEventReceived, this::onNoEventError);
    }

    private void onEventReceived(List<Event> events) {
        mEvents.addAll(events);
        mainView.bindEvents(mEvents);
        mainView.hideIndicator();
        mIsEventRequestRunning = false;
    }

    private void onNoEventError(Throwable throwable) {
        mIsEventRequestRunning = false;
        mainView.hideIndicator();
        mainView.onNoEventError();
    }

    public void loadingMoreEvent() {
        mIsEventRequestRunning = true;
        page++;
        mainView.showLoadingMoreEventIndicator();
        mSubscription = response.getUser()
                .flatMap(user -> response.getUserReceivedEvents(user.getName(), page))
                .subscribe(this::onMoreEventReceived, this::onEventError);
    }

    private void onMoreEventReceived(List<Event> newEvents) {
        mEvents.addAll(newEvents);
        mainView.updateEvents(newEvents.size());
        mainView.hideLoadingMoreEventIndicator();
        mIsEventRequestRunning = false;

    }

    private void onEventError(Throwable throwable) {
        mIsEventRequestRunning = false;
        mainView.showLoadingErrorView();
    }

    public void onEventsEndReached() {
        if (!mIsEventRequestRunning)
            loadingMoreEvent();
    }

    private void showErrorMsg(Throwable throwable) {
        mainView.showErrorView(throwable.getMessage());
    }

    private void onAuthFailed(Throwable throwable) {
        token.delete();
        mainView.onAuthFailed();
    }

    public void onErrorRetryRequest() {
        if (mEvents.size() == 0) {
            askForEvent();
        } else {
            loadingMoreEvent();
        }
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
        mainView.hideIndicator();
        mainView.hideLoadingMoreEventIndicator();
        mainView.hideErrorView();
        mSubscription.unsubscribe();
        mIsEventRequestRunning = false;
    }

    @Override
    public void onCreate() {
        askForEvent();
    }
}
