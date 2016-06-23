package com.chendoing.gitcode.injector.modules;

import com.chendoing.gitcode.data.api.GithubResponse;
import com.chendoing.gitcode.injector.Activity;
import com.chendoing.gitcode.presenters.RepositoryActivityPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryActivityModule {

    private String userName;

    private String repoName;

    public RepositoryActivityModule(String userName,String repo) {
        this.userName = userName;
        this.repoName = repo;
    }

    @Provides
    @Activity
    public RepositoryActivityPresenter providesPresenter(GithubResponse response){
        return new RepositoryActivityPresenter(userName,repoName,response);
    }
}
