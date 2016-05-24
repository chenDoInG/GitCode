package com.chendoing.gitcode.injector.modules;

import com.chendoing.gitcode.data.api.oauth.AccessToken;
import com.chendoing.gitcode.injector.Activity;
import com.f2prateek.rx.preferences.Preference;

import dagger.Module;
import dagger.Provides;

@Module
public class SplashModule {

    @Provides
    Preference<String> providesToken(@AccessToken Preference<String> token){
        return token;
    }
}
