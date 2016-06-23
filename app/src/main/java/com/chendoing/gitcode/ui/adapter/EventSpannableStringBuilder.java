package com.chendoing.gitcode.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.view.View;

import com.chendoing.gitcode.R;
import com.chendoing.gitcode.ui.OnClickableSpannedClickListener;
import com.chendoing.gitcode.ui.OnRepositorySpannedClickListener;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by chenDoInG on 16/5/26.
 */
public class EventSpannableStringBuilder extends SpannableStringBuilder {

    private final static String LINK_COLOR = "#0066B3";

    private EventSpannableStringBuilder() {
        //You cannot get the instance.
    }

    public static class Builder {

        public EventSpannableStringBuilder builder = new EventSpannableStringBuilder();

        public Builder user(String content, OnClickableSpannedClickListener onClickListener) {
            builder.append(newSpan(content, onClickListener));
            return this;
        }

        public Builder repository(String content, OnRepositorySpannedClickListener onClickListener) {
            List<String> repo = Lists.newArrayList(Splitter.on("/").split(content));
            builder.append(newSpan(content, repo.get(0), repo.get(1), onClickListener));
            return this;
        }

        public Builder type(String content) {
            builder.append(" ").append(content).append(" ");
            return this;
        }

        public Builder image(Context context, int resourceId) {
            builder.append(" ");
            builder.setSpan(new ImageSpan(context, resourceId), 0, 1, 0);
            return this;
        }

        public EventSpannableStringBuilder build() {
            return builder;
        }

        private SpannableString newSpan(String content, OnClickableSpannedClickListener onClickListener) {
            ClickableSpan span = new ClickableSpan() {
                @Override
                public void updateDrawState(TextPaint ds) {
                    ds.setColor(Color.parseColor(LINK_COLOR));
                    ds.setUnderlineText(false);
                }

                @Override
                public void onClick(View widget) {
                    if (onClickListener != null)
                        onClickListener.onClick(content);
                }

            };
            SpannableString spannableString = new SpannableString(content);
            spannableString.setSpan(span, 0, content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return spannableString;
        }

        private SpannableString newSpan(String content, String userName, String repo, OnRepositorySpannedClickListener onClickListener) {
            ClickableSpan span = new ClickableSpan() {
                @Override
                public void updateDrawState(TextPaint ds) {
                    ds.setColor(Color.parseColor(LINK_COLOR));
                    ds.setUnderlineText(false);
                }

                @Override
                public void onClick(View widget) {
                    if (onClickListener != null)
                        onClickListener.onClick(userName, repo);
                }

            };
            SpannableString spannableString = new SpannableString(content);
            spannableString.setSpan(span, 0, content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return spannableString;
        }
    }
}
