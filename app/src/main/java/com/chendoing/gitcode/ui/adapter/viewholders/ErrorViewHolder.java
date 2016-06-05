package com.chendoing.gitcode.ui.adapter.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chendoing.gitcode.R;
import com.chendoing.gitcode.data.api.model.Event;
import com.chendoing.gitcode.ui.adapter.EventViewHolderListener;

import butterknife.BindView;

/**
 * Created by chenDoInG on 16/6/5.
 */
public class ErrorViewHolder extends RecyclerView.ViewHolder implements EventViewHolderListener {

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
