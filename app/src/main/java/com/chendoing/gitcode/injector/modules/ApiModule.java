package com.chendoing.gitcode.injector.modules;

import com.chendoing.gitcode.data.api.GithubService;
import com.chendoing.gitcode.injector.Activity;
import com.google.gson.GsonBuilder;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {

    @Provides
    @Activity
    Retrofit providesRetrofit() {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().serializeNulls().generateNonExecutableJson().create()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(GithubService.BASE_URL)
                .build();
    }
}
