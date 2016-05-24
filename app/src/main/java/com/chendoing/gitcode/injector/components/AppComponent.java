package com.chendoing.gitcode.injector.components;

import com.chendoing.gitcode.GitCodeApplication;
import com.chendoing.gitcode.injector.modules.AppModule;
import com.f2prateek.rx.preferences.Preference;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;
import rx.Scheduler;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    GitCodeApplication app();

    Preference<String> token();

    @Named("ui_thread")
    Scheduler uiThread();

    @Named("executor_thread")
    Scheduler executorThread();
}
