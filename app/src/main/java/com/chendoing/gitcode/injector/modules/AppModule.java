package com.chendoing.gitcode.injector.modules;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.chendoing.gitcode.GitCodeApplication;
import com.chendoing.gitcode.data.api.GithubResponse;
import com.chendoing.gitcode.data.api.GithubService;
import com.chendoing.gitcode.data.api.model.User;
import com.chendoing.gitcode.data.api.oauth.OauthInterceptor;
import com.f2prateek.rx.preferences.Preference;
import com.f2prateek.rx.preferences.RxSharedPreferences;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;

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
import retrofit2.converter.jackson.JacksonConverterFactory;
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
    @Singleton
    User providesUser(){
        return new User();
    }

    @Provides
    OkHttpClient provideOkHttpClient(OauthInterceptor oauthInterceptor) {
        File cacheDir = new File(mGitCodeApplication.getCacheDir(), "http");
        Cache cache = new Cache(cacheDir, DISK_CACHE_SIZE);

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(oauthInterceptor)
                .addInterceptor(loggingInterceptor)
                .build();
    }

    @Provides
    Retrofit providesRetrofit(OkHttpClient okHttpClient) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JodaModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return new Retrofit.Builder()
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
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
