package com.chendoing.gitcode.injector.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Module
public class NetModule {

    private String mBaseUrl;

    public NetModule(String url){
        this.mBaseUrl = url;
    }

    @Provides
    @Singleton
    Retrofit providesRetrofit(){
        return new Retrofit.Builder()
                .addConverterFactory(JacksonConverterFactory.create())
                .baseUrl(mBaseUrl)
                .build();
    }
}
