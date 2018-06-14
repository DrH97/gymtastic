package com.thetechtriad.drh.gymtastic.model;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.List;

public class UserResponse {

    @SerializedName("status")
    private String status;

    @SerializedName("email")
    private String[] email;

    @SerializedName("password")
    private String[] password;

    @SerializedName("message")
    private String message;

    @SerializedName("user")
    private User user;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public String[] getEmail() {
        return email;
    }

    public void setEmail(String[] email) {
        this.email = email;
    }

    public String[] getPassword() {
        return password;
    }

    public void setPassword(String[] password) {
        this.password = password;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
