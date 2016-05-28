package com.chendoing.gitcode.presenters.views;

import com.chendoing.gitcode.data.api.model.Event;

import java.util.List;

/**
 * Created by chenDoInG on 16/5/25.
 */
public interface MainView extends View {

    void bindEvents(List<Event> events);

    void updateEvents(int eventsAdd);

    void onEventsEndReach();

    void showLoadingErrorView();

    void showErrorView(String msg);

    void hideErrorView();

    void showIndicator();

    void hideIndicator();

    void showLoadingMoreEventIndicator();

    void hideLoadingMoreEventIndicator();

    void onAuthFailed();

    void onNoEventError();
}
