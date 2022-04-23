package com.example.a_yard.ui.dashboard;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public class Msg {
    public static final int MSG_RECEIVE = 0;
    public static final int MSG_SEND = 1;

    private int type;
    private String content;
    private static String url;

    public static String getUrl() {
        return url;
    }

    public static void setUrl(String url) {
        Msg.url = url;
    }

    public Msg(String content, int type) {
        this.content = content;
        this.type = type;
    }

    public String getMessage() {
        return content;
    }
    public int getType() {
        return type;
    }

}