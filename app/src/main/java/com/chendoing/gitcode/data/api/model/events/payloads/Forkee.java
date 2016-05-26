package com.chendoing.gitcode.data.api.model.events.payloads;

/**
 * Created by chenDoInG on 16/5/26.
 */
public class Forkee {

    private String name;
    private int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Forkee{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
