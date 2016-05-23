package com.chendoing.gitcode.injector.components;

import android.content.SharedPreferences;

import com.chendoing.gitcode.GitCodeApplication;
import com.chendoing.gitcode.injector.modules.AppModule;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;
import rx.Scheduler;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    GitCodeApplication app();

    SharedPreferences sp();

    @Named("ui_thread")
    Scheduler uiThread();

    @Named("executor_thread")
    Scheduler executorThread();
}
