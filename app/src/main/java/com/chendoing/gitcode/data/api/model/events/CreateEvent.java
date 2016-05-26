package com.chendoing.gitcode.data.api.model.events;

import com.chendoing.gitcode.R;
import com.chendoing.gitcode.data.api.model.events.Event;
import com.chendoing.gitcode.data.api.model.events.payloads.CreatEventPayload;

/**
 * Created by chenDoInG on 16/5/26.
 */
public class CreateEvent extends Event {

    private CreatEventPayload payload;

    public CreatEventPayload getPayload() {
        return payload;
    }

    public void setPayload(CreatEventPayload payload) {
        this.payload = payload;
    }

    @Override
    public int getDrableId() {
        return R.drawable.mega_icon_create;
    }

    @Override
    public String toString() {
        return "CreateEvent{" +
                "payload=" + payload +
                '}';
    }
}
