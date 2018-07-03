package com.thetechtriad.drh.gymtastic.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.thetechtriad.drh.gymtastic.R;
import com.thetechtriad.drh.gymtastic.model.Workout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WorkoutsAdapter extends RecyclerView.Adapter<WorkoutsAdapter.MyViewHolder> {

    private Context context;
    Random random;
    private ArrayList<Integer> workoutImages = new ArrayList<>();

    private List<Workout> workoutList;

    public WorkoutsAdapter(Context context, List<Workout> workoutList) {
        this.context = context;
        this.workoutList = workoutList;

        for (int i = 1; i < 5; i++) {
            workoutImages.add(context.getResources().getIdentifier("workout"+i, "drawable", context.getPackageName()));
        }

        random = new Random();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView workoutDate, exercise, location, reps, sets;
        public ImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);
            workoutDate = itemView.findViewById(R.id.workout_date);
            exercise = itemView.findViewById(R.id.exercise);
            location = itemView.findViewById(R.id.location);
            reps = itemView.findViewById(R.id.reps);
            sets = itemView.findViewById(R.id.sets);
            image = itemView.findViewById(R.id.photo_image_view);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.workout_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Workout workout = workoutList.get(position);
        holder.workoutDate.setText(workout.getWorkoutDate());
        holder.exercise.setText(workout.getExerciseType());
        holder.location.setText(String.format("%d", workout.getLocationId()));
        holder.reps.setText(workout.getReps().toString() + " " +context.getString(R.string.reps));
        holder.sets.setText(workout.getSets().toString() + " " +context.getString(R.string.sets));

        int rand = random.nextInt(4);

        Glide.with(context)
                .load(workoutImages.get(rand))
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return workoutList.size();
    }

}
