package com.chendoing.gitcode.injector.modules;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.chendoing.gitcode.GitCodeApplication;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@Module
public class AppModule {

    private final GitCodeApplication mGitCodeApplication;

    public AppModule(GitCodeApplication gitCodeApplication) {
        this.mGitCodeApplication = gitCodeApplication;
    }

    @Provides
    @Singleton
    GitCodeApplication provideGitCodeApplication() {
        return mGitCodeApplication;
    }

    @Provides
    @Singleton
    SharedPreferences providesSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(mGitCodeApplication);
    }

    @Provides
    @Named("executor_thread")
    Scheduler provideExecutorThread() {
        return Schedulers.newThread();
    }

    @Provides
    @Named("ui_thread")
    Scheduler provideUiThread() {
        return AndroidSchedulers.mainThread();
    }
}
