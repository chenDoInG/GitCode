package com.chendoing.gitcode.data.api.model.payload;

import com.chendoing.gitcode.data.api.model.Repository;
import com.chendoing.gitcode.data.api.model.User;

/**
 * Created by chenDoInG on 16/5/27.
 */
public abstract class Payloaded {

    private User sender;
    private Repository repository;

    public abstract int getDrawable();
    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public Repository getRepository() {
        return repository;
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
    }
}
