package com.chendoing.gitcode.data.api;

import com.chendoing.gitcode.data.api.model.Event;
import com.chendoing.gitcode.data.api.model.Token;
import com.chendoing.gitcode.data.api.model.User;

import java.util.List;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by chenDoInG on 16/5/24.
 */
public interface GithubService {

    String BASE_URL = "https://api.github.com";

    @Headers("Accept: application/json")
    @POST("https://github.com/login/oauth/access_token")
    Observable<Token> getUserToken(@Query("client_id") String clientId, @Query("client_secret") String clientSecret, @Query("code") String code);

    @GET("events")
    Observable<List<Event>> getEvents();

    @GET("users/{userName}/received_events")
    Observable<Response<List<Event>>> getUserReceivedEvents(@Path("userName") String userName, @Query("page")int page);

    @GET("user")
    Observable<User> getUser();
}
