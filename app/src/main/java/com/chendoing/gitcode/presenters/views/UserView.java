package com.chendoing.gitcode.presenters.views;

import com.chendoing.gitcode.data.api.model.User;

/**
 * Created by chenDoInG on 16/6/2.
 */
public interface UserView extends View {

    void bindView(User user);
}
