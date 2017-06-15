package com.ericho.coupleshare.mvp;

import java.util.Date;

/**
 * Created by steve_000 on 10/6/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.mvp
 */

public class Location {

    private int id;
    private Double latitude;
    private Double longitude;
    private Date date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
