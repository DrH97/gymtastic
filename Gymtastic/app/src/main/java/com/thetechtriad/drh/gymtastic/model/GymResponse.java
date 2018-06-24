package com.thetechtriad.drh.gymtastic.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GymResponse {

    @SerializedName("total_results")
    private Integer totalResults;

    @SerializedName("results")
    private List<Gym> gyms;

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public List<Gym> getGyms() {
        return gyms;
    }

    public void setGyms(List<Gym> gyms) {
        this.gyms = gyms;
    }
}
