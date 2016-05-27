/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.chendoing.gitcode.ui.adapter;

import android.content.Context;
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
import com.chendoing.gitcode.data.api.model.Event;
import com.chendoing.gitcode.data.api.model.payload.ForkEvent;
import com.chendoing.gitcode.data.api.model.payload.MemberEvent;
import com.chendoing.gitcode.data.api.model.payload.PullRequestEvent;
import com.chendoing.gitcode.data.api.model.payload.WatchEvent;
import com.chendoing.gitcode.ui.OnClickableSpannedClickListener;
import com.chendoing.gitcode.utils.TimeUtil;
import com.google.common.base.Joiner;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserReceivedEventListAdapter extends RecyclerView.Adapter<UserReceivedEventListAdapter.BaseEventViewHolder> {

    private final List<Event> mEvents;
    private final OnClickableSpannedClickListener mUserClickListener;
    private final OnClickableSpannedClickListener mRepositoryClickListener;

    private Context mContext;

    public UserReceivedEventListAdapter(Context context, List<Event> events, OnClickableSpannedClickListener userClickListener, OnClickableSpannedClickListener repositoryClickListener) {
        mEvents = events;
        mContext = context;
        mUserClickListener = userClickListener;
        mRepositoryClickListener = repositoryClickListener;
    }

    @Override
    public BaseEventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case 1:
                return new PullRequestViewHolder(LayoutInflater.from(mContext).inflate(
                        R.layout.item_event_pullrequest, parent, false
                ));
            default:
                return new EventViewHolder(LayoutInflater.from(mContext).inflate(
                        R.layout.item_event, parent, false
                ));
        }
    }

    @Override
    public void onBindViewHolder(BaseEventViewHolder holder, int position) {
        holder.bindEvent(mEvents.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        if (PullRequestEvent.class.getSimpleName().equals(mEvents.get(position).getType())) {
            return 1;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mEvents.size();
    }

    public abstract class BaseEventViewHolder extends RecyclerView.ViewHolder {

        public BaseEventViewHolder(View itemView) {
            super(itemView);
        }

        public abstract void bindEvent(Event event);
    }

    public class PullRequestViewHolder extends BaseEventViewHolder {

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
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bindEvent(Event event) {
            System.out.println(event);
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

    public class EventViewHolder extends BaseEventViewHolder {

        @BindView(R.id.item_event_description)
        TextView eventDescription;
        @BindView(R.id.item_event_thumb)
        CircleImageView eventThumb;
        @BindView(R.id.item_event_type)
        ImageView eventType;
        @BindView(R.id.item_event_time)
        TextView eventTime;

        public EventViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
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

        private SpannableStringBuilder getEventDesc(Event event) {
            System.out.println(event);
            switch (event.getType()) {
                case "ForkEvent":
                    return new EventSpannableStringBuilder.Builder()
                            .user(event.getActor().getLogin(), mUserClickListener)
                            .type("forked")
                            .repository(event.getRepo().getName(), mRepositoryClickListener)
                            .type("to")
                            .repository(((ForkEvent) event.getPayload()).getForkee().getFull_name(), mRepositoryClickListener)
                            .build();
                case "WatchEvent":
                    return new EventSpannableStringBuilder.Builder()
                            .user(event.getActor().getLogin(), mUserClickListener)
                            .type(((WatchEvent) event.getPayload()).getAction())
                            .repository(event.getRepo().getName(), mRepositoryClickListener)
                            .build();
                case "MemberEvent":
                    return new EventSpannableStringBuilder.Builder()
                            .user(event.getActor().getLogin(), mUserClickListener)
                            .type(((MemberEvent) event.getPayload()).getAction())
                            .user(((MemberEvent) event.getPayload()).getMember().getLogin(), mUserClickListener)
                            .type("to")
                            .repository(event.getRepo().getName(), mRepositoryClickListener)
                            .build();
                case "CreateEvent":
                    return new EventSpannableStringBuilder.Builder()
                            .user(event.getActor().getLogin(), mUserClickListener)
                            .type("create repository")
                            .repository(event.getRepo().getName(), mRepositoryClickListener)
                            .build();
            }
            return new SpannableStringBuilder("json parse error");
        }

    }
}
