package com.chendoing.gitcode.ui.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.chendoing.gitcode.utils.TextTypeFaceUtil;

/**
 * Created by chenDoInG on 16/6/1.
 */
public class GithubTypefaceTextView extends TextView {

    public GithubTypefaceTextView(Context context) {
        super(context);
        initTypeface(context);
    }

    public GithubTypefaceTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initTypeface(context);
    }

    public GithubTypefaceTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initTypeface(context);
    }

    private void initTypeface(Context context) {
        setTypeface(TextTypeFaceUtil.getGithubTypeface(context));
    }
}
