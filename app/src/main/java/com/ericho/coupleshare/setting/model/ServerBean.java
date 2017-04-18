package com.ericho.coupleshare.setting.model;

import android.support.annotation.DrawableRes;

/**
 * Created by EricH on 18/4/2017.
 */

public class ServerBean {
    private String url;//
    private String displayName;//
    @DrawableRes
    private int resourceInteger;

    public ServerBean(){}
    public ServerBean(String displayName,String url,@DrawableRes int resourceInteger){
        this.displayName = displayName;
        this.url = url;
        this.resourceInteger = resourceInteger;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    @DrawableRes
    public int getResourceInteger() {
        return resourceInteger;
    }

    public void setResourceInteger(@DrawableRes int resourceInteger) {
        this.resourceInteger = resourceInteger;
    }
}
