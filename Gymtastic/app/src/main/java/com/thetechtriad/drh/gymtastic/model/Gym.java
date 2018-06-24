package com.thetechtriad.drh.gymtastic.model;

import com.google.gson.annotations.SerializedName;

public class Gym {

    @SerializedName("id")
    private Integer id;

    @SerializedName("name")
    private String name;

    @SerializedName("opening_time")
    private String opening_time;

    @SerializedName("closing_time")
    private String closing_time;

    @SerializedName("lat")
    private Double latitude;

    @SerializedName("long")
    private Double longitude;

    @SerializedName("days")
    private String days;

    public Gym(Integer id, String name, String opening_time, String closing_time, Double latitude, Double longitude, String days) {
        this.id = id;
        this.name = name;
        this.opening_time = opening_time;
        this.closing_time = closing_time;
        this.latitude = latitude;
        this.longitude = longitude;
        this.days = days;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOpening_time() {
        return opening_time;
    }

    public void setOpening_time(String opening_time) {
        this.opening_time = opening_time;
    }

    public String getClosing_time() {
        return closing_time;
    }

    public void setClosing_time(String closing_time) {
        this.closing_time = closing_time;
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

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }
}