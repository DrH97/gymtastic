package com.thetechtriad.drh.gymtastic.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WorkoutResponse {

    @SerializedName("status")
    private String status;

    @SerializedName("total_results")
    private Integer totalResults;

    @SerializedName("results")
    private List<Workout> workouts;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public List<Workout> getWorkouts() {
        return workouts;
    }

    public void setWorkouts(List<Workout> workouts) {
        this.workouts = workouts;
    }
}
