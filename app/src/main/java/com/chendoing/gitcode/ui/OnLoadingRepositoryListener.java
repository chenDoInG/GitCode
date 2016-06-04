package com.chendoing.gitcode.ui;

import com.chendoing.gitcode.data.api.model.Repository;

/**
 * Created by chenDoInG on 16/6/4.
 */
public interface OnLoadingRepositoryListener {

    Repository load(String userName, String repo);
}
