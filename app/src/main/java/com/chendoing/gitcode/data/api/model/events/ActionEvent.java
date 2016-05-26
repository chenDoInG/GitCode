package com.chendoing.gitcode.data.api.model.events;

import com.chendoing.gitcode.R;
import com.chendoing.gitcode.data.api.model.events.payloads.CommonPayload;

/**
 * Created by chenDoInG on 16/5/26.
 */
public class ActionEvent extends Event {

    private CommonPayload payload;

    @Override
    public int getDrableId() {
        return R.drawable.mega_icon_fork;
    }
}
