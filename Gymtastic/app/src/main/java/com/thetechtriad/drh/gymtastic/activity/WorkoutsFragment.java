package com.thetechtriad.drh.gymtastic.activity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
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
public class WorkoutsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

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
    private ProgressBar mProgressBar;
    private SwipeRefreshLayout mySwipeRefreshLayout;

    public WorkoutsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment WorkoutsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WorkoutsFragment newInstance() {
        WorkoutsFragment fragment = new WorkoutsFragment();
        Bundle args = new Bundle();
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

        view.findViewById(R.id.tv_no_workouts).setVisibility(View.VISIBLE);
        mySwipeRefreshLayout = view.findViewById(R.id.workoutsswiperrefresh);
        mySwipeRefreshLayout.setOnRefreshListener(this);
//        mListener.onFragmentInteraction(getUserId);
        id = ((MainActivity)this.getActivity()).userId;
        recycler = view.findViewById(R.id.workoutsRecycler);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        recycler.setLayoutManager(layoutManager);

        wAdapter = new WorkoutsAdapter(getContext(), workoutList);

        recycler.setAdapter(wAdapter);

        prepareWorkoutData();
        return view;
    }

    public void prepareWorkoutData() {
        if (mProgressBar != null)
            showLoader(true);

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

    }

    private void setWorkoutData(List<Workout> workouts) {
        if (workouts != null){
            workoutList.clear();
            for (int i=0; i < workouts.size(); i++)
                workoutList.add(workouts.get(i));

            getView().findViewById(R.id.tv_no_workouts).setVisibility(View.VISIBLE);

            wAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(getActivity(), "Couldn't get any workouts", Toast.LENGTH_SHORT).show();
        }

//        showLoader(false);
        mySwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mProgressBar = view.findViewById(R.id.pbWorkoutFragment);
        showLoader(true);

    }

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

    @Override
    public void onRefresh() {

        prepareWorkoutData();
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
        void onFragmentInteraction(Uri uri);
    }

    private void showLoader(boolean b) {
        if (b) {
            Log.e("MF", "Showing loader");
            mProgressBar.setVisibility(View.VISIBLE);
        } else {

            Log.e("MF", "Removing loader");
            mProgressBar.setVisibility(View.GONE);
        }
    }
}
