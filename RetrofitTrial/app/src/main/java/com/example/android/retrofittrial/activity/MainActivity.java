package com.example.android.retrofittrial.activity;

import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.android.retrofittrial.R;
import com.example.android.retrofittrial.model.Gym;
import com.example.android.retrofittrial.model.GymResponse;
import com.example.android.retrofittrial.model.Movie;
import com.example.android.retrofittrial.model.MovieResponse;
import com.example.android.retrofittrial.rest.ApiClient;
import com.example.android.retrofittrial.rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private final static String API_KEY = "1c029015f68aa07385ada687e5a0eeda";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (API_KEY.isEmpty()) {
            Toast.makeText(this, "Please obtain API Key first", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

//        Call<MovieResponse> call = apiInterface.getTopRatedMovies(API_KEY);
        Call<GymResponse> call = apiInterface.getGymDeets();

//        call.enqueue(new Callback<MovieResponse>() {
//            @Override
//            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
//                List<Movie> movies = response.body().getResults();
//                Log.d(TAG, "Number of Movies Received: " + movies.size());
//            }
//
//            @Override
//            public void onFailure(Call<MovieResponse> call, Throwable t) {
//                Log.e(TAG, t.toString());
//            }
//        });

        call.enqueue(new Callback<GymResponse>() {
            @Override
            public void onResponse(Call<GymResponse> call, Response<GymResponse> response) {
                List<Gym> gyms = response.body().getResults();
                Log.d(TAG, "Number of gyms received: " + gyms.get(0).getName());
            }

            @Override
            public void onFailure(Call<GymResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }
}
