package com.chendoing.gitcode.injector.components;

import com.chendoing.gitcode.injector.Activity;
import com.chendoing.gitcode.injector.modules.RepositoryActivityModule;
import com.chendoing.gitcode.presenters.RepositoryActivityPresenter;
import com.chendoing.gitcode.ui.activities.RepositoryActivity;

import dagger.Component;

@Activity
@Component(dependencies = AppComponent.class, modules = RepositoryActivityModule.class)
public interface RepositoryActivityComponent {

    void inject(RepositoryActivity activity);

    RepositoryActivityPresenter presenter();
}
