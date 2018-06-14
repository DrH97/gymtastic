package com.thetechtriad.drh.gymtastic.rest;

import com.thetechtriad.drh.gymtastic.model.User;
import com.thetechtriad.drh.gymtastic.model.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("users/login")
    Call<UserResponse> attemptAuth(@Field("email") String email, @Field("password") String password);

    @POST("users/logout")
    Call<UserResponse> attemptLogout();

}
