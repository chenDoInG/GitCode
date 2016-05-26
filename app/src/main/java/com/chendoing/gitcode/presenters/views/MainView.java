package com.chendoing.gitcode.presenters.views;

import com.chendoing.gitcode.data.api.model.events.Event;

import java.util.List;

/**
 * Created by chenDoInG on 16/5/25.
 */
public interface MainView extends View {

    void showErrorView(String msg);

    void bindEventList(List<Event> events);

    void showEventListView();

    void hideEventListView();

    void showIndicator();

    void hideIndicator();
}
