/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.chendoing.gitcode.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chendoing.gitcode.R;
import com.chendoing.gitcode.data.api.model.Event;
import com.chendoing.gitcode.ui.RecyclerClickListener;
import com.google.common.base.Joiner;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserReceivedEventListAdapter extends RecyclerView.Adapter<UserReceivedEventListAdapter.UserReceivedEventViewHolder> {

    private final RecyclerClickListener mRecyclerListener;
    private final List<Event> mEvents;

    private Context mContext;

    public UserReceivedEventListAdapter(Context context, List<Event> events,
                                        RecyclerClickListener recyclerClickListener) {
        mEvents = events;
        mContext = context;
        mRecyclerListener = recyclerClickListener;
    }

    @Override
    public UserReceivedEventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(mContext).inflate(
                R.layout.item_event, parent, false);

        return new UserReceivedEventViewHolder(rootView, mRecyclerListener);
    }

    @Override
    public void onBindViewHolder(UserReceivedEventViewHolder holder, int position) {
        holder.bindAvenger(mEvents.get(position));
    }

    @Override
    public int getItemCount() {
        return mEvents.size();
    }

    public class UserReceivedEventViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_view)
        TextView mTextView;

        public UserReceivedEventViewHolder(View itemView, final RecyclerClickListener recyclerClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            bindListener(itemView, recyclerClickListener);
        }

        public void bindAvenger(Event event) {
            System.out.println(event);
            String text = Joiner.on("-").join(new String[]{event.getActor().getLogin(), event.getType(), event.getRepo().getUrl()});
            mTextView.setText(text);
        }

        private void bindListener(View itemView, final RecyclerClickListener recyclerClickListener) {
            itemView.setOnClickListener(v ->
                    recyclerClickListener.onElementClick(getAdapterPosition(), mTextView));
        }
    }
}
