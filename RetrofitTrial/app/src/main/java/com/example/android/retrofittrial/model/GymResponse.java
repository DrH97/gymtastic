package com.example.android.retrofittrial.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GymResponse {

    @SerializedName("results")
    private List<Gym> results;
    @SerializedName("total_results")
    private int totalResults;

    public List<Gym> getResults() {
        return results;
    }

    public void setResults(List<Gym> results) {
        this.results = results;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }
}
