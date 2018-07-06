package com.thetechtriad.drh.gymtastic.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.thetechtriad.drh.gymtastic.R;
import com.thetechtriad.drh.gymtastic.model.Instructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class InstructorsAdapter extends RecyclerView.Adapter<InstructorsAdapter.viewHolder> {

    private Context context;
    Random random;
    private ArrayList<Integer> instructorImages = new ArrayList<>();

    private List<Instructor> instructorList;

    public InstructorsAdapter(Context context, List<Instructor> instructorList) {
        this.context = context;
        this.instructorList = instructorList;

        for (int i = 1; i < 5; i++) {
            instructorImages.add(context.getResources().getIdentifier("instructor"+i, "drawable", context.getPackageName()));
        }

        random = new Random();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        public TextView name, details, gender, call, email;
        public ImageView image;

        public viewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.instructor_name);
            details = itemView.findViewById(R.id.instructor_details);
            gender = itemView.findViewById(R.id.instructor_gender);
            call = itemView.findViewById(R.id.instructor_call);
            email = itemView.findViewById(R.id.instructor_email);
            image = itemView.findViewById(R.id.instructor_image);
        }
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.instructor_list_row, parent, false);

        return new viewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        final Instructor instructor = instructorList.get(position);
        holder.name.setText(instructor.getName());
        holder.details.setText(instructor.getGymLocationId());
        holder.gender.setText(instructor.getGender());
//        holder.call.setText(String.format("%s "+R.string.reps, workout.getReps().toString()));
//        holder.email.setText(String.format("%s sets"+R.string.sets, workout.getSets().toString()));

        int rand = random.nextInt(4);

        Glide.with(context)
                .load(instructorImages.get(rand))
                .into(holder.image);

        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + instructor.getContacts()));
                if (intent.resolveActivity(context.getPackageManager())!= null)
                    context.startActivity(intent);
            }
        });

        holder.email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:" + instructor.getEmail()));
//                intent.putExtra(Intent.EXTRA_EMAIL, Utils.getUsermail(context));
                intent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.app_name));
                if (intent.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return instructorList.size();
    }
}