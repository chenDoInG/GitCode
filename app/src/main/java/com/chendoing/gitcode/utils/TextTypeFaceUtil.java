package com.chendoing.gitcode.utils;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by chenDoInG on 16/6/1.
 */
public class TextTypeFaceUtil {

    private static Typeface mTypeface;

    public static Typeface getGithubTypeface(Context context) {
        if (mTypeface == null) {
            mTypeface = Typeface.createFromAsset(context.getAssets(), "octicons.ttf");
        }
        return mTypeface;
    }
}
