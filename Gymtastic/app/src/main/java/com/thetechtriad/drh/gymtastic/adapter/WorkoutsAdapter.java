package com.thetechtriad.drh.gymtastic.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thetechtriad.drh.gymtastic.R;
import com.thetechtriad.drh.gymtastic.model.Workout;

import java.util.List;

public class WorkoutsAdapter extends RecyclerView.Adapter<WorkoutsAdapter.MyViewHolder> {

    private List<Workout> workoutList;

    public WorkoutsAdapter(List<Workout> workoutList) {
        this.workoutList = workoutList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView workoutDate, exercise, location, reps, sets;

        public MyViewHolder(View itemView) {
            super(itemView);
            workoutDate = itemView.findViewById(R.id.workout_date);
            exercise = itemView.findViewById(R.id.exercise);
            location = itemView.findViewById(R.id.location);
            reps = itemView.findViewById(R.id.reps);
            sets = itemView.findViewById(R.id.sets);
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
        holder.location.setText(""+workout.getLocationId());
        holder.reps.setText(workout.getReps().toString() + "reps");
        holder.sets.setText(workout.getSets().toString() + "sets");
    }

    @Override
    public int getItemCount() {
        return workoutList.size();
    }

}
