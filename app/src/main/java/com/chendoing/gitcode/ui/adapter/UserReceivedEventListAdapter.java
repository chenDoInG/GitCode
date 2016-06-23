/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.chendoing.gitcode.ui.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chendoing.gitcode.R;
import com.chendoing.gitcode.data.api.GithubResponse;
import com.chendoing.gitcode.data.api.model.Event;
import com.chendoing.gitcode.data.api.model.payload.ForkEvent;
import com.chendoing.gitcode.data.api.model.payload.IssueCommentEvent;
import com.chendoing.gitcode.data.api.model.payload.MemberEvent;
import com.chendoing.gitcode.data.api.model.payload.PullRequestEvent;
import com.chendoing.gitcode.data.api.model.payload.WatchEvent;
import com.chendoing.gitcode.ui.OnClickableSpannedClickListener;
import com.chendoing.gitcode.ui.OnRepositorySpannedClickListener;
import com.chendoing.gitcode.utils.TimeUtil;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.makeramen.roundedimageview.RoundedImageView;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class UserReceivedEventListAdapter extends RecyclerView.Adapter<UserReceivedEventListAdapter.EventViewHolder> {

    private final List<Event> mEvents;
    private final OnClickableSpannedClickListener mUserClickListener;
    private final OnRepositorySpannedClickListener mRepositoryClickListener;
    private final GithubResponse mGithubResponse;

    private Context mContext;

    public UserReceivedEventListAdapter(Context context,
                                        List<Event> events,
                                        OnClickableSpannedClickListener userClickListener,
                                        OnRepositorySpannedClickListener repositoryClickListener,
                                        GithubResponse githubResponse) {
        mEvents = events;
        mContext = context;
        mUserClickListener = userClickListener;
        mRepositoryClickListener = repositoryClickListener;
        mGithubResponse = githubResponse;
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return getViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {
        holder.bindEvent(mEvents.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        try {
            return ItemViewType.valueOf(
                    Joiner.on("").join(mEvents.get(position).getType(), "ViewHolder")
            ).getViewType();
        } catch (IllegalArgumentException e) {
            return 0;
        }
    }

    @Override
    public int getItemCount() {
        return mEvents.size();
    }

    private enum ItemViewType {
        ErrorViewHolder(0, R.layout.item_event_parse_error),
        PullRequestViewHolder(1, R.layout.item_event_pullrequest),
        WatchEventViewHolder(2, R.layout.item_event),
        ForkEventViewHolder(3, R.layout.item_event),
        MemberEventViewHolder(4, R.layout.item_event),
        CreateEventViewHolder(5, R.layout.item_event),
        IssueCommentEventViewHolder(6, R.layout.item_event_issuecomment),
        PublicEventViewHolder(7, R.layout.item_event_issuecomment),;

        ItemViewType(int viewType, int resourceId) {
            this.viewType = viewType;
            this.resourceId = resourceId;
        }

        private int viewType;

        private int resourceId;

        public int getResourceId() {
            return resourceId;
        }

        public int getViewType() {
            return viewType;
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private EventViewHolder getViewHolder(ViewGroup parent, int viewType) {
        ItemViewType type = ItemViewType.ErrorViewHolder;
        for (ItemViewType itemType : ItemViewType.values()) {
            if (itemType.viewType == viewType) {
                type = itemType;
            }
        }
        View itemView = LayoutInflater.from(mContext).inflate(type.getResourceId(), parent, false);
        try {
            Class eventViewHolderClass =
                    Class.forName(
                            Joiner.on("$").join(this.getClass().getName(), type.name())
                    );
            eventViewHolderClass.getConstructors();
            //noinspection unchecked
            Constructor constructor = eventViewHolderClass.getConstructor(UserReceivedEventListAdapter.class, View.class);
            return (EventViewHolder) constructor.newInstance(this, itemView);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
            Timber.e(e.getMessage());
            throw new RuntimeException("cannot reach here");
        }
    }

    public abstract class EventViewHolder extends RecyclerView.ViewHolder {

        public EventViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public abstract void bindEvent(Event event);
    }

    public class ErrorViewHolder extends EventViewHolder {

        @BindView(R.id.item_event_parse_error_description)
        TextView error;

        public ErrorViewHolder(View itemView) {
            super(itemView);
        }


        @Override
        public void bindEvent(Event event) {
            error.setText("cannot parse");
        }
    }

    public class PullRequestViewHolder extends EventViewHolder {

        @BindView(R.id.item_pullrequest_comment)
        TextView pullRequestComment;
        @BindView(R.id.item_pullrequest_description)
        TextView pullRequestDesc;
        @BindView(R.id.item_pullrequest_detail)
        TextView pullRequestDetail;
        @BindView(R.id.item_pullrequest_thumb)
        ImageView pullRequestThumb;
        @BindView(R.id.item_pullrequest_time)
        TextView pullRequestTime;
        @BindView(R.id.item_pullrequest_type)
        ImageView getPullRequestType;

        public PullRequestViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindEvent(Event event) {
            PullRequestEvent pullRequestEvent = (PullRequestEvent) event.getPayload();
            Glide.with(mContext)
                    .load(event.getActor().getAvatar_url())
                    .crossFade()
                    .into(pullRequestThumb);
            Glide.with(mContext)
                    .load(event.getPayload().getDrawable())
                    .crossFade()
                    .into(getPullRequestType);
            pullRequestTime.setText(TimeUtil.getDuration(event.getCreated_at()));
            pullRequestDesc.setText(new EventSpannableStringBuilder.Builder()
                    .user(event.getActor().getLogin(), mUserClickListener)
                    .type(pullRequestEvent.getAction())
                    .type("pull request")
                    .repository(event.getRepo().getName(), mRepositoryClickListener)
                    .build()
            );
            pullRequestDesc.setMovementMethod(LinkMovementMethod.getInstance());
            pullRequestComment.setText(pullRequestEvent.getPull_request().getBody());


            pullRequestDetail.setText(
                    new EventSpannableStringBuilder.Builder()
                            .image(mContext, R.drawable.mini_icon_commit)
                            .type(pullRequestEvent.getPull_request().getCommits() + " commits")
                            .type(pullRequestEvent.getPull_request().getAdditions() + " additions")
                            .type(pullRequestEvent.getPull_request().getDeletions() + " deletions")
                            .build()
            );
        }
    }

    public class IssueCommentEventViewHolder extends EventViewHolder {

        @BindView(R.id.item_issuecomment_comment)
        TextView issueComment;
        @BindView(R.id.item_issuecomment_description)
        TextView issueDesc;
        @BindView(R.id.item_issuecomment_thumb)
        RoundedImageView issueThumb;
        @BindView(R.id.item_issuecomment_time)
        TextView issueTime;
        @BindView(R.id.item_issuecomment_type)
        ImageView issumeType;

        public IssueCommentEventViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindEvent(Event event) {
            IssueCommentEvent payload = (IssueCommentEvent) event.getPayload();
            Glide.with(mContext)
                    .load(event.getActor().getAvatar_url())
                    .crossFade()
                    .into(issueThumb);
            Glide.with(mContext)
                    .load(event.getPayload().getDrawable())
                    .crossFade()
                    .into(issumeType);
            issueDesc.setText(new EventSpannableStringBuilder.Builder()
                    .user(event.getActor().getLogin(), mUserClickListener)
                    .type("commented on pull request")
                    .repository(event.getRepo().getName(), mRepositoryClickListener)
                    .build()
            );
            issueTime.setText(TimeUtil.getDuration(event.getCreated_at()));
            issueComment.setText(payload.getComment().getBody());
        }
    }

    public class PublicEventViewHolder extends IssueCommentEventViewHolder {

        public PublicEventViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindEvent(Event event) {
            Timber.d(event.toString());
            Glide.with(mContext)
                    .load(event.getActor().getAvatar_url())
                    .crossFade()
                    .into(issueThumb);
            Glide.with(mContext)
                    .load(event.getPayload().getDrawable())
                    .crossFade()
                    .into(issumeType);
            issueDesc.setText(new EventSpannableStringBuilder.Builder()
                    .user(event.getActor().getLogin(), mUserClickListener)
                    .type("made")
                    .repository(event.getRepo().getName(), mRepositoryClickListener)
                    .type("public")
                    .build()
            );
            issueTime.setText(TimeUtil.getDuration(event.getCreated_at()));
            List<String> nameAndRepo = Lists.newArrayList(Splitter.on("/").split(event.getRepo().getName()));
            mGithubResponse.getUserRepository(nameAndRepo.get(0), nameAndRepo.get(1))
                    .subscribe(repository -> issueComment.setText(repository.getDescription()));

        }
    }


    public class CreateEventViewHolder extends ActionEventViewHolder {

        public CreateEventViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public SpannableStringBuilder getEventDesc(Event event) {
            return new EventSpannableStringBuilder.Builder()
                    .user(event.getActor().getLogin(), mUserClickListener)
                    .type("create repository")
                    .repository(event.getRepo().getName(), mRepositoryClickListener)
                    .build();
        }
    }

    public class WatchEventViewHolder extends ActionEventViewHolder {

        public WatchEventViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public SpannableStringBuilder getEventDesc(Event event) {
            WatchEvent watchEvent = (WatchEvent) event.getPayload();
            return new EventSpannableStringBuilder.Builder()
                    .user(event.getActor().getLogin(), mUserClickListener)
                    .type(watchEvent.getAction())
                    .repository(event.getRepo().getName(), mRepositoryClickListener)
                    .build();
        }
    }

    public class ForkEventViewHolder extends ActionEventViewHolder {

        public ForkEventViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public SpannableStringBuilder getEventDesc(Event event) {
            return new EventSpannableStringBuilder.Builder()
                    .user(event.getActor().getLogin(), mUserClickListener)
                    .type("forked")
                    .repository(event.getRepo().getName(), mRepositoryClickListener)
                    .type("to")
                    .repository(((ForkEvent) event.getPayload()).getForkee().getFull_name(), mRepositoryClickListener)
                    .build();
        }
    }

    public class MemberEventViewHolder extends ActionEventViewHolder {

        public MemberEventViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public SpannableStringBuilder getEventDesc(Event event) {
            return new EventSpannableStringBuilder.Builder()
                    .user(event.getActor().getLogin(), mUserClickListener)
                    .type(((MemberEvent) event.getPayload()).getAction())
                    .user(((MemberEvent) event.getPayload()).getMember().getLogin(), mUserClickListener)
                    .type("to")
                    .repository(event.getRepo().getName(), mRepositoryClickListener)
                    .build();
        }
    }

    public abstract class ActionEventViewHolder extends EventViewHolder {

        @BindView(R.id.item_event_description)
        TextView eventDescription;
        @BindView(R.id.item_event_thumb)
        RoundedImageView eventThumb;
        @BindView(R.id.item_event_type)
        ImageView eventType;
        @BindView(R.id.item_event_time)
        TextView eventTime;

        public ActionEventViewHolder(View itemView) {
            super(itemView);
        }

        public void bindEvent(Event event) {
            Glide.with(mContext)
                    .load(event.getActor().getAvatar_url())
                    .crossFade()
                    .into(eventThumb);
            Glide.with(mContext)
                    .load(event.getPayload().getDrawable())
                    .crossFade()
                    .into(eventType);
            eventTime.setText(TimeUtil.getDuration(event.getCreated_at()));
            eventDescription.setText(getEventDesc(event));
            eventDescription.setMovementMethod(LinkMovementMethod.getInstance());
        }

        public abstract SpannableStringBuilder getEventDesc(Event event);

    }
}
