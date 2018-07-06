package com.thetechtriad.drh.gymtastic.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.thetechtriad.drh.gymtastic.R;
import com.thetechtriad.drh.gymtastic.Utils;
import com.thetechtriad.drh.gymtastic.activity.WorkoutsFragment;
import com.thetechtriad.drh.gymtastic.model.Workout;
import com.thetechtriad.drh.gymtastic.model.WorkoutResponse;
import com.thetechtriad.drh.gymtastic.rest.ApiClient;
import com.thetechtriad.drh.gymtastic.rest.ApiInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkoutsAdapter extends RecyclerView.Adapter<WorkoutsAdapter.MyViewHolder> {

    private static final String TAG = WorkoutsFragment.class.getSimpleName();

    private Context context;
    Random random;
    private ArrayList<Integer> workoutImages = new ArrayList<>();

    private List<Workout> workoutList;

    private int mShortAnimTime;

    private Utils utils;

    public WorkoutsAdapter(Context context, List<Workout> workoutList) {
        this.context = context;
        this.workoutList = workoutList;
        utils = new Utils((Activity)context);
        mShortAnimTime = context.getResources().getInteger(android.R.integer.config_shortAnimTime);

        for (int i = 1; i < 10; i++) {
            workoutImages.add(context.getResources().getIdentifier("w"+i, "drawable", context.getPackageName()));
        }

        random = new Random();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView workoutDate, exercise, location, reps, sets;
        public ImageView image, delete, favourite;
        public View overlay;
        public CardView workoutCard;
        public RelativeLayout relativeLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            relativeLayout = itemView.findViewById(R.id.workout_card_parent);
            workoutCard = itemView.findViewById(R.id.workout_card);
            workoutDate = itemView.findViewById(R.id.workout_date);
            exercise = itemView.findViewById(R.id.exercise);
            location = itemView.findViewById(R.id.location);
            reps = itemView.findViewById(R.id.reps);
            sets = itemView.findViewById(R.id.sets);
            image = itemView.findViewById(R.id.photo_image_view);
            overlay = itemView.findViewById(R.id.workout_overlay);
            overlay.setY(overlay.getHeight());
            delete = itemView.findViewById(R.id.workout_delete);
            favourite = itemView.findViewById(R.id.workout_favourite);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    overlay.setAlpha(0f);
                    overlay.setVisibility(View.VISIBLE);
//                    overlay.setY(overlay.getHeight());

                    overlay.animate()
                            .translationY(0)
                            .alpha(1f)
                            .setDuration(mShortAnimTime)
                            .setListener(null);
                }
            });

            overlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    overlay.animate()
                            .translationY(v.getHeight())
                            .alpha(0f)
                            .setDuration(mShortAnimTime)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    overlay.setVisibility(View.GONE);
                                }
                            });
                }
            });

            final ImageView share = itemView.findViewById(R.id.workout_share);
            share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {shareWorkout(exercise.getText().toString(), location.getText().toString(), sets.getText().toString(), reps.getText().toString());
                }
            });

        }
    }

    private void shareWorkout(String exercise, String location, String sets, String reps) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, exercise + "\n" + location + "\n" + sets + "\n" + reps + "\n");

        context.startActivity(intent);
    }

//    private void shareWorkout(RelativeLayout workoutCard) {
//        if (ActivityCompat.checkSelfPermission(context, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
//            utils.toastMessage("Permissions not granted");
//
//        try {
////            utils.toastMessage("sharing workout "+ workoutCard.getMeasuredHeight());
//            workoutCard.setDrawingCacheEnabled(true);
//            Bitmap bitmap = loadBitmapFromView(workoutCard);
////            Bitmap bitmap = Bitmap.createBitmap(workoutCard.getDrawingCache());
////            workoutCard.setDrawingCacheEnabled(false);
//
//            String mPath = Environment.getExternalStorageDirectory().toString()+"/Pictures/sidi.png";
//
//            File imageFile = new File(mPath);
//            imageFile.createNewFile();
//            FileOutputStream outputStream = new FileOutputStream(imageFile);
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//
//            int quality = 100;
//
//            bitmap.compress(Bitmap.CompressFormat.PNG, quality, baos);
//            byte[] bytes = baos.toByteArray();
//
//            outputStream.write(bytes);
//
//            outputStream.flush();
//            outputStream.close();
//            utils.toastMessage("Finished sharing workout: "+ mPath);
//
//            openScreenshot(imageFile);
//        } catch (FileNotFoundException e) {
//            utils.toastMessage("File exc: " + e.getMessage());
//            e.printStackTrace();
//        } catch (IOException e) {
//            utils.toastMessage( "IOexc: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }

//    private void openScreenshot(File imageFile) {
//        Intent intent = new Intent();
//        intent.setAction(Intent.ACTION_VIEW);
//        Uri uri = Uri.fromFile(imageFile);
//        intent.setDataAndType(Uri.parse("content://"+imageFile), "image/*");
//        context.startActivity(intent);
//    }
//
//    private Bitmap loadBitmapFromView(View view) {
////        utils.toastMessage("loading bitmap " + view.getMeasuredHeight());
//
//        DisplayMetrics dm = context.getResources().getDisplayMetrics();
////        utils.toastMessage("loading bitmap dm: " + dm.widthPixels);
//
//        view.measure(View.MeasureSpec.makeMeasureSpec(dm.widthPixels, View.MeasureSpec.EXACTLY),
//                View.MeasureSpec.makeMeasureSpec(dm.heightPixels, View.MeasureSpec.EXACTLY));
//        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
////        view.buildDrawingCache(true);
////        Bitmap returnedBitmap = Bitmap.createBitmap(view.getDrawingCache());
////        Bitmap returnedBitmap = Bitmap.createBitmap(view.getLayoutParams().width, view.getLayoutParams().height, Bitmap.Config.ARGB_8888);
//        Bitmap returnedBitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.RGB_565);
//        Canvas c = new Canvas(returnedBitmap);
//        view.draw(c);
//
//        utils.toastMessage( "returning");
//        return returnedBitmap;
//    }

//    private Bitmap loadBitmapFromView(View view) {
////        utils.toastMessage( "loading Bitmap");
//        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
//
//        Canvas c = new Canvas(returnedBitmap);
//
////        utils.toastMessage( "canvas " + c.getHeight());
//        Drawable drawable = view.getBackground();
//        if (drawable!=null) {
////            utils.toastMessage("drawable ndani");
//            drawable.draw(c);
//        } else {
////            utils.toastMessage( "drawable out");
//            c.drawColor(Color.WHITE);
//        }
//
////        utils.toastMessage( "drawing view");
//        view.draw(c);
//
////        utils.toastMessage( "returning");
//        return returnedBitmap;
//    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.workout_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
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

        if (workoutList.get(position).isFavourite()){
            holder.favourite.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_favorite_24dp));
        } else {
            holder.favourite.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_favorite_border_accent_24dp));
        }

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteWorkout(workoutList.get(position).getId(), position);
                holder.overlay.animate()
                        .translationY(v.getHeight())
                        .alpha(0f)
                        .setDuration(mShortAnimTime)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                holder.overlay.setVisibility(View.GONE);
                            }
                        });
            }
        });

        //temporary favourite, can be persisted in future
        holder.favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (workoutList.get(position).isFavourite())
                    workoutList.get(position).setFavourite(false);
                else
                    workoutList.get(position).setFavourite(true);

            }
        });
    }

    private void deleteWorkout(Integer id, Integer position) {
//        utils.toastMessage("Deleting Workout: " + id);

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<WorkoutResponse> call = apiInterface.deleteWorkout(id);

        call.enqueue(new Callback<WorkoutResponse>() {
            @Override
            public void onResponse(Call<WorkoutResponse> call, Response<WorkoutResponse> response) {
//                utils.toastMessage("Workout deleted");
                utils.toastMessage(response.body().getMessage());

            }

            @Override
            public void onFailure(Call<WorkoutResponse> call, Throwable t) {
                utils.toastMessage(t.toString());
            }
        });

        workoutList.remove(position);

        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return workoutList.size();
    }

}
