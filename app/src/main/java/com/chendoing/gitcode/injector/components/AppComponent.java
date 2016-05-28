package com.chendoing.gitcode.injector.components;

import com.chendoing.gitcode.GitCodeApplication;
import com.chendoing.gitcode.data.api.model.User;
import com.chendoing.gitcode.injector.modules.AppModule;
import com.f2prateek.rx.preferences.Preference;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import rx.Scheduler;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    GitCodeApplication app();

    Preference<String> token();

    User user();

    Retrofit retrofit();

    OkHttpClient okHttpClient();

    @Named("ui_thread")
    Scheduler uiThread();

    @Named("executor_thread")
    Scheduler executorThread();
}
