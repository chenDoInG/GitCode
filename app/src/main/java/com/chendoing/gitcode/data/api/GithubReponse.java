package com.chendoing.gitcode.data.api;

import com.chendoing.gitcode.data.api.model.Token;

import javax.inject.Inject;

import okhttp3.HttpUrl;
import rx.Observable;
import rx.Scheduler;

/**
 * Created by chenDoInG on 16/5/24.
 */
public class GithubReponse {

    private final static String CLIENT_ID = "c7b408a20ee03b6a680a";
    private final static String CLIENT_SECRET = "3c7e4190344cf637f1f7f7999f35caf837ddd058";
    private final static String REDIRECT_URI = "https://github.com/chenDoInG/CodeHub/callback";
    public final static String AUTH_URI =
            HttpUrl.parse("https://github.com/login/oauth/authorize")
                    .newBuilder()
                    .addQueryParameter("client_id", CLIENT_ID)
                    .addQueryParameter("redirect_uri", REDIRECT_URI)
                    .build().toString();

    private Scheduler uiThread;
    private Scheduler executorThread;
    private GithubService githubService;

    @Inject
    public GithubReponse(Scheduler uiThread, Scheduler executorThread, GithubService githubService) {
        this.githubService = githubService;
        this.uiThread = uiThread;
        this.executorThread = executorThread;
    }

    public Observable<Token> getUserToken(String code) {
        return githubService.getUserToken(CLIENT_ID, CLIENT_SECRET, code)
                .subscribeOn(executorThread)
                .observeOn(uiThread);
    }

    public Observable getEvents(){
        return githubService.getEvents()
                .subscribeOn(executorThread)
                .observeOn(uiThread);
    }

}
