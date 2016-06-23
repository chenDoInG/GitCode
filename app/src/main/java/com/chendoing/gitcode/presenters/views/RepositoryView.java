package com.chendoing.gitcode.presenters.views;

import com.chendoing.gitcode.data.api.model.Repository;

/**
 * Created by chenDoInG on 16/6/23.
 */
public interface RepositoryView extends View {

    void bindView(Repository repository);
}
