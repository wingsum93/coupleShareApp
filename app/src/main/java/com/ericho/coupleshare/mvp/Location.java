package com.ericho.coupleshare.mvp;

import com.google.gson.annotations.SerializedName;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.util.Date;

import kotlin.jvm.JvmField;

/**
 * Created by steve_000 on 10/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.mvp
 */
@Table(name = "Location")
public class Location {

    public Location(){}
    
    @SerializedName("id")
    @Column(name = "id",isId = true,autoGen = false)
    private Integer id;
    
    @SerializedName("username")
    @Column(name = "username")
    private String username;
    
    @SerializedName("uploadBy")
    @Column(name = "uploadBy")
    private String uploadBy;
    
    @SerializedName("attitude")
    @Column(name = "attitude")
    private Double attitude;
    
    @SerializedName("latitude")
    @Column(name = "latitude")
    private Double latitude;
    
    @SerializedName("longitude")
    @Column(name = "longitude")
    private Double longitude;
    
    @SerializedName("accurate")
    @Column(name = "accurate")
    private Integer accurate;
    
    @SerializedName("collectDate")
    @Column(name = "date")
    private Date date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUploadBy() {
        return uploadBy;
    }

    public void setUploadBy(String uploadBy) {
        this.uploadBy = uploadBy;
    }

    public Double getAttitude() {
        return attitude;
    }

    public void setAttitude(Double attitude) {
        this.attitude = attitude;
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

    public Integer getAccurate() {
        return accurate;
    }

    public void setAccurate(Integer accurate) {
        this.accurate = accurate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
