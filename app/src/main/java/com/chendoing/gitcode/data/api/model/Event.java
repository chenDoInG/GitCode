package com.chendoing.gitcode.data.api.model;

/**
 * Created by chenDoInG on 16/5/25.
 */
public class Event {

    private String type;
    private String created_at;
    private String id;
    private Repo repo;
    private Payload payload;
    private Actor actor;

    public Payload getPlayload() {
        return payload;
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public void setPlayload(Payload payload) {
        this.payload = payload;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Repo getRepo() {
        return repo;
    }

    public void setRepo(Repo repo) {
        this.repo = repo;
    }

    @Override
    public String toString() {
        return "Event{" +
                "type='" + type + '\'' +
                ", created_at='" + created_at + '\'' +
                ", id='" + id + '\'' +
                ", repo=" + repo +
                ", payload=" + payload +
                '}';
    }
}
