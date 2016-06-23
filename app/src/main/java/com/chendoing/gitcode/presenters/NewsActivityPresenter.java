package com.chendoing.gitcode.presenters;

import android.text.TextUtils;

import com.chendoing.gitcode.data.api.GithubResponse;
import com.chendoing.gitcode.data.api.model.Event;
import com.chendoing.gitcode.data.api.model.Repository;
import com.chendoing.gitcode.data.api.model.User;
import com.chendoing.gitcode.injector.Activity;
import com.chendoing.gitcode.presenters.views.NewsView;
import com.chendoing.gitcode.presenters.views.View;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscription;
import rx.functions.Action1;
import timber.log.Timber;

/**
 * Created by chenDoInG on 16/5/25.
 */
@Activity
public class NewsActivityPresenter implements Presenter {

    private NewsView newsView;

    private GithubResponse response;

    private boolean mIsEventRequestRunning;

    private int page;

    private Subscription mSubscription;

    private List<Event> mEvents = new ArrayList<>();

    private User mUser;

    private boolean isLastPage;

    @Inject
    public NewsActivityPresenter(GithubResponse reponse, User user) {
        this.response = reponse;
        this.mUser = user;
        page = 1;
    }

    public void refreshEvents() {
        if (mUser == null || TextUtils.isEmpty(mUser.getLogin())) {
            newsView.onAuthFailed();
            return;
        }
        newsView.hideErrorView();
        mSubscription = response.getUserReceivedEvents(mUser.getLogin(), 1)
                .subscribe(this::onRefreshEventReceived);
    }

    private void onRefreshEventReceived(List<Event> refreshEvent) {
        mEvents.addAll(0, refreshEvent);
        newsView.refreshEvents();
    }

    public void askForEvent() {
        if (mUser == null || TextUtils.isEmpty(mUser.getLogin())) {
            newsView.onAuthFailed();
            return;
        }
        mIsEventRequestRunning = true;
        newsView.hideErrorView();
        newsView.showIndicator(mUser);
        mSubscription = response.getUserReceivedEvents(mUser.getLogin(), 1)
                .subscribe(this::onEventReceived, this::onNoEventError);
    }

    private void onEventReceived(List<Event> events) {
        mEvents.addAll(events);
        newsView.bindEvents(mEvents);
        newsView.hideIndicator();
        mIsEventRequestRunning = false;
    }

    private void onNoEventError(Throwable throwable) {
        Timber.e(throwable.getMessage());
        mIsEventRequestRunning = false;
        newsView.hideErrorView();
        newsView.hideIndicator();
        newsView.hideEventView();
        newsView.onNoEventError();
    }

    public void loadingMoreEvent() {
        mIsEventRequestRunning = true;
        page++;
        newsView.showLoadingMoreEventIndicator();
        mSubscription = response.getUserReceivedEvents(mUser.getLogin(), page)
                .subscribe(this::onMoreEventReceived, this::onEventError);
    }

    private void onMoreEventReceived(List<Event> newEvents) {
        if (newEvents.size() == 0) {
            isLastPage = true;
            newsView.onEventsEndReach();
        } else {
            mEvents.addAll(newEvents);
            newsView.updateEvents(newEvents.size());
            newsView.hideLoadingMoreEventIndicator();
        }
        mIsEventRequestRunning = false;
    }

    private void onEventError(Throwable throwable) {
        mIsEventRequestRunning = false;
        newsView.hideLoadingMoreEventIndicator();
        newsView.hideErrorView();
        newsView.showLoadingErrorView();
    }

    public void onEventsEndReached() {
        if (!mIsEventRequestRunning) {
            if (isLastPage) {
                newsView.onEventsEndReach();
            } else
                loadingMoreEvent();
        }
    }

    private void showErrorMsg(Throwable throwable) {
        newsView.showErrorView(throwable.getMessage());
    }

    public void onErrorRetryRequest() {
        newsView.showEventView();
        if (mEvents.size() == 0) {
            askForEvent();
        } else {
            loadingMoreEvent();
        }
    }

    @Override
    public void attachView(View view) {
        newsView = (NewsView) view;
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
        newsView.hideIndicator();
        newsView.hideLoadingMoreEventIndicator();
        newsView.hideErrorView();
        if (mSubscription != null)
            mSubscription.unsubscribe();
        mIsEventRequestRunning = false;
    }

    @Override
    public void onCreate() {
    }
}
