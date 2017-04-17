package com.ericho.coupleshare.http.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by steve_000 on 15/4/2017.
 */
public class BaseSingleResponse<T> {
    @SerializedName("status")
    private boolean status;
    @SerializedName("extra")
    private T extra;
    @SerializedName("errorMessage")
    private String errorMessage;
    @SerializedName("otherMessage")
    private String otherMessage;//use when success;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public T getExtra() {
        return extra;
    }

    public void setExtra(T extra) {
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
