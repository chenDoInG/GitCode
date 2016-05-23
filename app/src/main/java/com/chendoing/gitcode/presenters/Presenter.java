package com.chendoing.gitcode.presenters;

import com.chendoing.gitcode.presenters.views.View;

/**
 * Created by chenDoInG on 16/5/23.
 */
public interface Presenter {

    void onStart();

    void onStop();

    void onPause();

    void onCreate();

    void attachView(View view);
}
