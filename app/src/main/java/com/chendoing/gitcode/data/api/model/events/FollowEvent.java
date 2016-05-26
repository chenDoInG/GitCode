package com.chendoing.gitcode.data.api.model.events;

import com.chendoing.gitcode.R;

/**
 * Created by chenDoInG on 16/5/26.
 */
public class FollowEvent extends Event {

    private Follow payload;
    private class Follow{
        private String target;

        public String getTarget() {
            return target;
        }

        public void setTarget(String target) {
            this.target = target;
        }

        @Override
        public String toString() {
            return "Follow{" +
                    "target='" + target + '\'' +
                    '}';
        }
    }

    @Override
    public int getDrableId() {
        return R.drawable.mega_icon_follow;
    }

    @Override
    public String toString() {
        return "FollowEvent{" +
                "payload=" + payload +
                '}';
    }
}
