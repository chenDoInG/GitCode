package com.chendoing.gitcode.data.api.model.payload;

import com.chendoing.gitcode.R;
import com.chendoing.gitcode.data.api.model.Forkee;

public class DownloadEvent extends Payloaded {

    private Forkee forkee;

    public Forkee getForkee() {
        return forkee;
    }

    public void setForkee(Forkee forkee) {
        this.forkee = forkee;
    }

    @Override
    public String toString() {
        return "DownloadEvent{" +
                "forkee=" + forkee +
                '}';
    }

    @Override
    public int getDrawable() {
        return R.drawable.mega_icon_download;
    }
}
