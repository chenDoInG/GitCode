package com.chendoing.gitcode.injector.modules;

import android.app.Application;
import android.content.SharedPreferences;

import com.chendoing.gitcode.GitCodeApplication;
import com.chendoing.gitcode.data.api.GithubResponse;
import com.chendoing.gitcode.data.api.GithubService;
import com.chendoing.gitcode.data.api.oauth.AccessToken;
import com.chendoing.gitcode.data.api.oauth.OauthInterceptor;
import com.chendoing.gitcode.injector.Activity;
import com.f2prateek.rx.preferences.Preference;
import com.f2prateek.rx.preferences.RxSharedPreferences;
import com.google.gson.GsonBuilder;

import java.io.File;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.content.Context.MODE_PRIVATE;
import static com.jakewharton.byteunits.DecimalByteUnit.MEGABYTES;

@Module
public class AppModule {

    private final GitCodeApplication mGitCodeApplication;

    public AppModule(GitCodeApplication gitCodeApplication) {
        this.mGitCodeApplication = gitCodeApplication;
    }

    static final int DISK_CACHE_SIZE = (int) MEGABYTES.toBytes(50);

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences() {
        return mGitCodeApplication.getSharedPreferences("gitcode", MODE_PRIVATE);
    }

    @Provides
    @Singleton
    RxSharedPreferences provideRxSharedPreferences(SharedPreferences prefs) {
        return RxSharedPreferences.create(prefs);
    }

    @Provides
    @Singleton
    Preference<String> provideAccessToken(RxSharedPreferences prefs) {
        return prefs.getString("access-token");
    }

    @Provides
    OkHttpClient provideOkHttpClient(OauthInterceptor oauthInterceptor) {
        File cacheDir = new File(mGitCodeApplication.getCacheDir(), "http");
        Cache cache = new Cache(cacheDir, DISK_CACHE_SIZE);

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(oauthInterceptor)
                .addInterceptor(logging)
                .build();
    }

    @Provides
    Retrofit providesRetrofit(OkHttpClient okHttpClient) {

        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().serializeNulls().generateNonExecutableJson().create()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(GithubService.BASE_URL)
                .client(okHttpClient)
                .build();
    }

    @Provides
    GithubResponse providesGithubResponse(GithubResponse response) {
        return response;
    }

    @Provides
    @Singleton
    GitCodeApplication provideGitCodeApplication() {
        return mGitCodeApplication;
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
