package com.ericho.coupleshare.mvp;

import java.util.Date;

/**
 * Created by steve_000 on 23/6/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.mvp
 */

public class Photo {

    private String photoName;
    private Date uploadDate;
    private String path;
    private String suffix;
    private int imageInt;

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public int getImageInt() {
        return imageInt;
    }

    public void setImageInt(int imageInt) {
        this.imageInt = imageInt;
    }
}
