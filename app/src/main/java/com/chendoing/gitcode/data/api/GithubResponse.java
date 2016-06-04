package com.chendoing.gitcode.data.api;

import com.chendoing.gitcode.data.api.model.Event;
import com.chendoing.gitcode.data.api.model.Repository;
import com.chendoing.gitcode.data.api.model.Token;
import com.chendoing.gitcode.data.api.model.User;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import okhttp3.HttpUrl;
import retrofit2.Response;
import retrofit2.Retrofit;
import rx.Observable;
import rx.Scheduler;

/**
 * Created by chenDoInG on 16/5/24.
 */
public class GithubResponse {

    private final static String CLIENT_ID = "c7b408a20ee03b6a680a";
    private final static String CLIENT_SECRET = "1fb7a531020d7b74f72b19b16c815ef4a732da42";
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
    public GithubResponse(@Named("ui_thread") Scheduler uiThread, @Named("executor_thread") Scheduler executorThread, Retrofit retrofit) {
        this.githubService = retrofit.create(GithubService.class);
        this.uiThread = uiThread;
        this.executorThread = executorThread;
    }

    public Observable<Token> getUserToken(String code) {
        return githubService.getUserToken(CLIENT_ID, CLIENT_SECRET, code)
                .subscribeOn(executorThread)
                .observeOn(uiThread);
    }

    public Observable<List<Event>> getEvents() {
        return githubService.getEvents()
                .subscribeOn(executorThread)
                .observeOn(uiThread);
    }


    public Observable<User> getUser() {
        return githubService.getUser()
                .subscribeOn(executorThread)
                .observeOn(uiThread);
    }

    public Observable<List<Event>> getUserReceivedEvents(String userName, int page) {
        return githubService.getUserReceivedEvents(userName, page)
                .subscribeOn(executorThread)
                .observeOn(uiThread);
    }

    public Observable<Repository> getUserRepository(String userName, String repo) {
        return githubService.getUserRepository(userName, repo)
                .subscribeOn(executorThread)
                .observeOn(uiThread);
    }
}
