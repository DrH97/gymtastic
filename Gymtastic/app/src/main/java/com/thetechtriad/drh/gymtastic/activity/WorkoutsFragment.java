package com.thetechtriad.drh.gymtastic.activity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.thetechtriad.drh.gymtastic.R;
import com.thetechtriad.drh.gymtastic.adapter.WorkoutsAdapter;
import com.thetechtriad.drh.gymtastic.model.Workout;
import com.thetechtriad.drh.gymtastic.model.WorkoutResponse;
import com.thetechtriad.drh.gymtastic.rest.ApiClient;
import com.thetechtriad.drh.gymtastic.rest.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WorkoutsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WorkoutsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorkoutsFragment extends Fragment {

    private static final String TAG = WorkoutsFragment.class.getSimpleName();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private List<Workout> workoutList = new ArrayList<>();
    private RecyclerView recycler;
    private WorkoutsAdapter wAdapter;
    private int id;

    private OnFragmentInteractionListener mListener;

    public WorkoutsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WorkoutsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WorkoutsFragment newInstance(String param1, String param2) {
        WorkoutsFragment fragment = new WorkoutsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
            }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_workouts, container, false);

//        mListener.onFragmentInteraction(getUserId);
        id = ((MainActivity)this.getActivity()).userId;
        recycler = view.findViewById(R.id.workoutsRecycler);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        recycler.setLayoutManager(layoutManager);

        wAdapter = new WorkoutsAdapter(workoutList);

        recycler.setAdapter(wAdapter);

        prepareWorkoutData();
        return view;
    }

    private void prepareWorkoutData() {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<WorkoutResponse> call = apiInterface.getWorkouts(id);

        call.enqueue(new Callback<WorkoutResponse>() {
            @Override
            public void onResponse(Call<WorkoutResponse> call, Response<WorkoutResponse> response) {
                List<Workout> workouts = response.body().getWorkouts();

                setWorkoutData(workouts);
//                Toast.makeText(getActivity(), "Successful Data Gotten", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<WorkoutResponse> call, Throwable t) {
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, t.toString());
            }
        });

//        Workout workout = new Workout(1, 1, 1, "2018-3-5", "Lifting", 8, 1);
//        workoutList.add(workout);
//
//        workout = new Workout(1, 1, 1, "2018-3-5", "Lifting", 8, 1);
//        workoutList.add(workout);
//
//        workout = new Workout(1, 1, 1, "2018-3-5", "Lifting", 8, 1);
//        workoutList.add(workout);
//
//        workout = new Workout(1, 1, 1, "2018-3-5", "Lifting", 8, 1);
//        workoutList.add(workout);
//
//        workout = new Workout(1, 1, 1, "2018-3-5", "Lifting", 8, 1);
//        workoutList.add(workout);
//
//        workout = new Workout(1, 1, 1, "2018-3-5", "Lifting", 8, 1);
//        workoutList.add(workout);
//
//        workout = new Workout(1, 1, 1, "2018-3-5", "Lifting", 8, 1);
//        workoutList.add(workout);
//
//        workout = new Workout(1, 1, 1, "2018-3-5", "Lifting", 8, 1);
//        workoutList.add(workout);
//
//        workout = new Workout(1, 1, 1, "2018-3-5", "Lifting", 8, 1);
//        workoutList.add(workout);
//
//        workout = new Workout(1, 1, 1, "2018-3-5", "Lifting", 8, 1);
//        workoutList.add(workout);
//
//        workout = new Workout(1, 1, 1, "2018-3-5", "Lifting", 8, 1);
//        workoutList.add(workout);
//
//        workout = new Workout(1, 1, 1, "2018-3-5", "Lifting", 8, 1);
//        workoutList.add(workout);

    }

    private void setWorkoutData(List<Workout> workouts) {
        if (workouts != null){
            for (int i=0; i < workouts.size(); i++)
                workoutList.add(workouts.get(i));

            wAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(getActivity(), "Couldn't get any workouts", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
