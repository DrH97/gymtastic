package com.thetechtriad.drh.gymtastic.rest;

import com.thetechtriad.drh.gymtastic.model.GymResponse;
import com.thetechtriad.drh.gymtastic.model.InstructorResponse;
import com.thetechtriad.drh.gymtastic.model.UserResponse;
import com.thetechtriad.drh.gymtastic.model.WorkoutResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("users/register")
    Call<UserResponse> registerUser(@Field("firstname") String firstname, @Field("lastname") String lastname,@Field("email") String email, @Field("password") String password, @Field("password_confirmation") String password_confirmation);

    @FormUrlEncoded
    @POST("users/login")
    Call<UserResponse> attemptAuth(@Field("email") String email, @Field("password") String password);

    @POST("users/logout")
    Call<UserResponse> attemptLogout();

    @GET("users/{id}")
    Call<UserResponse> getUserDetails(@Path("id") int user_id);

    @GET("gyms")
    Call<GymResponse> getGyms();

    @GET("instructors")
    Call<InstructorResponse> getInstructors();

    @GET("users/{id}/workouts")
    Call<WorkoutResponse> getWorkouts(@Path("id") Integer userId);

    @FormUrlEncoded
    @POST("workouts")
    Call<WorkoutResponse> addWorkout(@Field("user_id") int user_id, @Field("location_id") int location_id, @Field("workout_date") String date, @Field("exercise_type") String exercise, @Field("reps") int reps, @Field("sets") int sets);

    @POST("workouts/delete/{id}")
    Call<WorkoutResponse> deleteWorkout(@Path("id") int workout_id);

    @FormUrlEncoded
    @POST("users/{id}/gyms")
    Call<UserResponse> addUserLocation(@Path("id") Integer userId, @Field("location_id") int location_id);
}
