package com.chendoing.gitcode.data.api.model;

/**
 * Created by chenDoInG on 16/5/25.
 */
public class Payload {

    private String action;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "Playload{" +
                "action='" + action + '\'' +
                '}';
    }
}
