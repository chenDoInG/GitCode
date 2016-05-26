package com.chendoing.gitcode.data.api.model.events;

import com.chendoing.gitcode.data.api.model.Actor;
import com.chendoing.gitcode.data.api.model.Org;
import com.chendoing.gitcode.data.api.model.Repo;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(
                value = ActionEvent.class, name = "PullRequestEvent"
        ),
        @JsonSubTypes.Type(
                value = ActionEvent.class, name = "WatchEvent"
        ),
        @JsonSubTypes.Type(
                value = CreateEvent.class, name = "CreateEvent"
        ),
        @JsonSubTypes.Type(
                value = ActionEvent.class, name = "MemberEvent"
        ),
        @JsonSubTypes.Type(
                value = ForkEvent.class, name = "ForkEvent"
        )
})
public abstract class Event {

    private String id;
    private String created_at;
    private Repo repo;
    private Actor actor;
    private Org org;

    public  abstract int getDrableId();

    public Org getOrg() {
        return org;
    }

    public void setOrg(Org org) {
        this.org = org;
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
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
                "id='" + id + '\'' +
                ", created_at='" + created_at + '\'' +
                ", repo=" + repo +
                ", actor=" + actor +
                ", org=" + org +
                '}';
    }
}
