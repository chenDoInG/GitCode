package com.chendoing.gitcode.data.api.model.events;

import com.chendoing.gitcode.R;
import com.chendoing.gitcode.data.api.model.events.payloads.Forkee;

/**
 * Created by chenDoInG on 16/5/26.
 */
public class ForkEvent extends Event {

    private Forkee payload;

    @Override
    public int getDrableId() {
        return R.drawable.mega_icon_follow;
    }

    @Override
    public String toString() {
        return "ForkEvent{" +
                "payload=" + payload +
                '}';
    }

    public Forkee getPayload() {
        return payload;
    }

    public void setPayload(Forkee payload) {
        this.payload = payload;
    }

}
