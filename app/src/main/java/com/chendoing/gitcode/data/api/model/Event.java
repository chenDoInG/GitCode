package com.chendoing.gitcode.data.api.model;

import com.chendoing.gitcode.data.api.model.payload.CommitCommentEvent;
import com.chendoing.gitcode.data.api.model.payload.CreateEvent;
import com.chendoing.gitcode.data.api.model.payload.DeleteEvent;
import com.chendoing.gitcode.data.api.model.payload.DeploymentEvent;
import com.chendoing.gitcode.data.api.model.payload.DeploymentStatusEvent;
import com.chendoing.gitcode.data.api.model.payload.DownloadEvent;
import com.chendoing.gitcode.data.api.model.payload.ForkEvent;
import com.chendoing.gitcode.data.api.model.payload.IssueCommentEvent;
import com.chendoing.gitcode.data.api.model.payload.IssuesEvent;
import com.chendoing.gitcode.data.api.model.payload.MemberEvent;
import com.chendoing.gitcode.data.api.model.payload.MembershipEvent;
import com.chendoing.gitcode.data.api.model.payload.Payloaded;
import com.chendoing.gitcode.data.api.model.payload.PullRequestEvent;
import com.chendoing.gitcode.data.api.model.payload.PullRequestReviewCommentEvent;
import com.chendoing.gitcode.data.api.model.payload.PushEvent;
import com.chendoing.gitcode.data.api.model.payload.RepositoryEvent;
import com.chendoing.gitcode.data.api.model.payload.TeamAddEvent;
import com.chendoing.gitcode.data.api.model.payload.WatchEvent;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import org.joda.time.DateTime;

/**
 * type : Event
 * public : true
 * payload : {}
 * repo : {"id":3,"name":"octocat/Hello-World","url":"https://api.github.com/repos/octocat/Hello-World"}
 * actor : {"id":1,"login":"octocat","gravatar_id":"","avatar_url":"https://github.com/images/error/octocat_happy.gif","url":"https://api.github.com/users/octocat"}
 * org : {"id":1,"login":"github","gravatar_id":"","url":"https://api.github.com/orgs/github","avatar_url":"https://github.com/images/error/octocat_happy.gif"}
 * created_at : 2011-09-06T17:26:27Z
 * id : 12345
 */
public class Event {

    @JsonProperty("public")
    private boolean publicX;
    private String type;
    private String id;
    private DateTime created_at;
    private Repository repo;
    private User actor;
    private Org org;

    @JsonTypeInfo(
            use = JsonTypeInfo.Id.NAME,
            include = JsonTypeInfo.As.EXTERNAL_PROPERTY,
            property = "type",
            visible = true)
    @JsonSubTypes({
            @JsonSubTypes.Type(value = WatchEvent.class, name = "WatchEvent"),
            @JsonSubTypes.Type(value = CommitCommentEvent.class, name = "CommitCommentEvent"),
            @JsonSubTypes.Type(value = CreateEvent.class, name = "CreateEvent"),
            @JsonSubTypes.Type(value = DeleteEvent.class, name = "DeleteEvent"),
            @JsonSubTypes.Type(value = DeploymentEvent.class, name = "DeploymentEvent"),
            @JsonSubTypes.Type(value = DeploymentStatusEvent.class, name = "DeploymentStatusEvent"),
            @JsonSubTypes.Type(value = DownloadEvent.class, name = "DownloadEvent"),
            @JsonSubTypes.Type(value = ForkEvent.class, name = "ForkEvent"),
            @JsonSubTypes.Type(value = IssueCommentEvent.class, name = "IssueCommentEvent"),
            @JsonSubTypes.Type(value = IssuesEvent.class, name = "IssuesEvent"),
            @JsonSubTypes.Type(value = MemberEvent.class, name = "MemberEvent"),
            @JsonSubTypes.Type(value = MembershipEvent.class, name = "MembershipEvent"),
            @JsonSubTypes.Type(value = PullRequestEvent.class, name = "PullRequestEvent"),
            @JsonSubTypes.Type(value = PullRequestReviewCommentEvent.class, name = "PullRequestReviewCommentEvent"),
            @JsonSubTypes.Type(value = PushEvent.class, name = "PushEvent"),
            @JsonSubTypes.Type(value = RepositoryEvent.class, name = "RepositoryEvent"),
            @JsonSubTypes.Type(value = TeamAddEvent.class, name = "TeamAddEvent"),
            @JsonSubTypes.Type(value = WatchEvent.class,name = "PublicEvent")
    })
    private Payloaded payload;

    public boolean isPublicX() {
        return publicX;
    }

    public void setPublicX(boolean publicX) {
        this.publicX = publicX;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Org getOrg() {
        return org;
    }

    public void setOrg(Org org) {
        this.org = org;
    }

    public User getActor() {
        return actor;
    }

    public void setActor(User actor) {
        this.actor = actor;
    }


    public DateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(DateTime created_at) {
        this.created_at = created_at;
    }

    public Payloaded getPayload() {
        return payload;
    }

    public void setPayload(Payloaded payload) {
        this.payload = payload;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Repository getRepo() {
        return repo;
    }

    public void setRepo(Repository repo) {
        this.repo = repo;
    }

    @Override
    public String toString() {
        return "Event{" +
                "publicX=" + publicX +
                ", type='" + type + '\'' +
                ", id='" + id + '\'' +
                ", created_at=" + created_at +
                ", repo=" + repo +
                ", actor=" + actor +
                ", org=" + org +
                ", payload=" + payload +
                '}';
    }
}
