package com.chendoing.gitcode.presenters;

import android.text.TextUtils;

import com.chendoing.gitcode.data.api.GithubResponse;
import com.chendoing.gitcode.data.api.model.Event;
import com.chendoing.gitcode.data.api.model.User;
import com.chendoing.gitcode.injector.Activity;
import com.chendoing.gitcode.presenters.views.MainView;
import com.chendoing.gitcode.presenters.views.View;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Response;
import rx.Subscription;

/**
 * Created by chenDoInG on 16/5/25.
 */
@Activity
public class MainActivityPresenter implements Presenter {

    private MainView mainView;

    private GithubResponse response;

    private boolean mIsEventRequestRunning;

    private int page;

    private Subscription mSubscription;

    private List<Event> mEvents = new ArrayList<>();

    private User mUser;

    private boolean isLastPage;

    @Inject
    public MainActivityPresenter(GithubResponse reponse, User user) {
        this.response = reponse;
        this.mUser = user;
        page = 1;
    }

    public void refreshEvents() {
        if (mUser == null || TextUtils.isEmpty(mUser.getLogin())) {
            mainView.onAuthFailed();
            return;
        }
        mainView.hideErrorView();
        mSubscription = response.getUserReceivedEvents(mUser.getLogin(), 1)
                .subscribe(this::onRefreshEventReceived);
    }

    private void onRefreshEventReceived(List<Event> refreshEvent) {
        mEvents.addAll(0,refreshEvent);
        mainView.refreshEvents();
    }

    public void askForEvent() {
        if (mUser == null || TextUtils.isEmpty(mUser.getLogin())) {
            mainView.onAuthFailed();
            return;
        }
        mIsEventRequestRunning = true;
        mainView.hideErrorView();
        mainView.showIndicator(mUser);
        mSubscription = response.getUserReceivedEvents(mUser.getLogin(), 1)
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
        mainView.hideErrorView();
        mainView.hideIndicator();
        mainView.onNoEventError();
    }

    public void loadingMoreEvent() {
        mIsEventRequestRunning = true;
        page++;
        mainView.showLoadingMoreEventIndicator();
        mSubscription = response.getUserReceivedEvents(mUser.getLogin(), page)
                .subscribe(this::onMoreEventReceived, this::onEventError);
    }

    private void onMoreEventReceived(List<Event> newEvents) {
        if (newEvents.size() == 0) {
            isLastPage = true;
            mainView.onEventsEndReach();
        } else {
            mEvents.addAll(newEvents);
            mainView.updateEvents(newEvents.size());
            mainView.hideLoadingMoreEventIndicator();
        }
        mIsEventRequestRunning = false;
    }

    private void onEventError(Throwable throwable) {
        mIsEventRequestRunning = false;
        mainView.hideLoadingMoreEventIndicator();
        mainView.hideErrorView();
        mainView.showLoadingErrorView();
    }

    public void onEventsEndReached() {
        if (!mIsEventRequestRunning) {
            if (isLastPage) {
                mainView.onEventsEndReach();
            } else
                loadingMoreEvent();
        }
    }

    private void showErrorMsg(Throwable throwable) {
        mainView.showErrorView(throwable.getMessage());
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
        askForEvent();
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
    }
}
