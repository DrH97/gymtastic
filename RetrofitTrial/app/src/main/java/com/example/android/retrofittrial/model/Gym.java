package com.example.android.retrofittrial.model;

import com.google.gson.annotations.SerializedName;

public class Gym {

    @SerializedName("id")
    private Integer id;

    @SerializedName("name")
    private String name;

    @SerializedName("opening_time")
    private String openingTime;

    @SerializedName("closing_time")
    private String closingTime;

    @SerializedName("lat")
    private double latitude;

    @SerializedName("long")
    private double longitude;

    @SerializedName("days")
    private String days;

    public Gym(Integer id, String name, String openingTime, String closingTime, double latitude, double longitude, String days) {
        this.id = id;
        this.name = name;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
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

    public String getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(String openingTime) {
        this.openingTime = openingTime;
    }

    public String getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(String closingTime) {
        this.closingTime = closingTime;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }
}