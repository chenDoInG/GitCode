package com.chendoing.gitcode.data.api.model.payload;

import com.chendoing.gitcode.R;

/**
 * Created by chenDoInG on 16/6/5.
 */
public class PublicEvent extends Payloaded {

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "PublicEvent{" +
                "content='" + content + '\'' +
                '}';
    }

    @Override
    public int getDrawable() {
        return R.drawable.mega_icon_public_repo;
    }
}
