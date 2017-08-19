package com.ericho.coupleshare.model;

import java.io.Serializable;
import java.util.Date;

import paperparcel.PaperParcel;

/**
 * Created by steve_000 on 4/8/2017.
 * for project coupleShareApp
 * package name com.ericho.coupleshare.model
 */
@PaperParcel
@org.parceler.Parcel
public class Location123 implements Serializable {

    private Integer id;
    private String username;
    private Integer uploadBy;
    private Double attitude;
    private Double latitude;
    private Double longitude;
    private Double accurate;
    private Date date;
    private Boolean sync;





}
