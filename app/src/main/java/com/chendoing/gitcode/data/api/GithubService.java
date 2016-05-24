package com.chendoing.gitcode.data.api;

import com.chendoing.gitcode.data.api.model.Token;

import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by chenDoInG on 16/5/24.
 */
public interface GithubService {

    String TOKEN_URI = "https://github.com";

    @Headers("Accept: application/json")
    @POST("/login/oauth/access_token")
    Observable<Token> getUserToken(@Query("client_id") String clientId, @Query("client_secret") String clientSecret, @Query("code") String code);
}
