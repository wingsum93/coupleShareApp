package com.ericho.coupleshare.http.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by steve_000 on 15/4/2017.
 */
public class BaseResponse<T> {
    @SerializedName("status")
    private boolean status;
    @SerializedName("extra")
    private List<T> extra;

    @SerializedName("error_message")
    private String errorMessage;
    @SerializedName("other_message")
    private String otherMessage;//use when success;


    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<T> getExtra() {
        return extra;
    }

    public void setExtra(List<T> extra) {
        this.extra = extra;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getOtherMessage() {
        return otherMessage;
    }

    public void setOtherMessage(String otherMessage) {
        this.otherMessage = otherMessage;
    }
}
