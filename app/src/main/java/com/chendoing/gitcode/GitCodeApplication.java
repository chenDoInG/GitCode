package com.chendoing.gitcode;

import android.app.Application;

import com.chendoing.gitcode.injector.components.AppComponent;
import com.chendoing.gitcode.injector.components.DaggerAppComponent;
import com.chendoing.gitcode.injector.modules.AppModule;

/**
 * Created by chenDoInG on 16/5/23.
 */
public class GitCodeApplication extends Application {

    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initInjector();
    }

    private void initInjector() {
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
