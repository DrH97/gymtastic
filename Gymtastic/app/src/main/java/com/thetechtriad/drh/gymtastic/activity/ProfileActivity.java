package com.thetechtriad.drh.gymtastic.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.thetechtriad.drh.gymtastic.R;
import com.thetechtriad.drh.gymtastic.Utils;
import com.thetechtriad.drh.gymtastic.model.User;
import com.thetechtriad.drh.gymtastic.model.UserResponse;
import com.thetechtriad.drh.gymtastic.rest.ApiClient;
import com.thetechtriad.drh.gymtastic.rest.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends Activity {

    private static final String TAG = ProfileActivity.class.getSimpleName();

    private int user_id;

    private Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        utils = new Utils(this);

        user_id = Utils.getUserId(this);

        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNavigateUp();
            }
        });

        toolbar.setTitle("Profile");

        getUserData();
    }

    private void getUserData() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<UserResponse> call = apiInterface.getUserDetails(user_id);

        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                User user = response.body().getUsers().get(0);
//                utils.toastMessage(user.getEmail());
                setUserData(user);
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {

            }
        });
    }

    private void setUserData(User user) {
        EditText userFname = findViewById(R.id.user_firstname);
        EditText userLname = findViewById(R.id.user_lastname);
        EditText email = findViewById(R.id.user_email);
        EditText age = findViewById(R.id.user_age);
        Spinner gender = findViewById(R.id.user_gender);
        EditText weight = findViewById(R.id.user_weight);
        EditText target = findViewById(R.id.user_target);

        userFname.setText(user.getFirstname());
        userLname.setText(user.getLastname());
        email.setText(user.getEmail());
        if (user.getAge() != null)
            age.setText(user.getAge());

        if (user.getGender() != null)
            if (user.getGender().equals("M"))
                gender.setSelection(0);
            else if (user.getGender().equals("F"))
                gender.setSelection(1);
            else
                gender.setSelection(2);

        if (user.getWeight() > 0)
            weight.setText(String.valueOf(user.getWeight()));
        if (user.getTargetWeight() > 0)
            target.setText(String.valueOf(user.getTargetWeight()));

    }

    @Override
    public boolean onNavigateUp() {
        onBackPressed();
        return super.onNavigateUp();
    }
}
