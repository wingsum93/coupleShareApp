package com.ericho.coupleshare.mvp;

/**
 * Created by steve_000 on 26/6/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.mvp
 */

public class StatusBo {

    private int id;
    private String photoUrl;
    private String title;
    private String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
