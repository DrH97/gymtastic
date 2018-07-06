package com.thetechtriad.drh.gymtastic.activity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
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
import com.thetechtriad.drh.gymtastic.Utils;
import com.thetechtriad.drh.gymtastic.adapter.InstructorsAdapter;
import com.thetechtriad.drh.gymtastic.model.Instructor;
import com.thetechtriad.drh.gymtastic.model.InstructorResponse;
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
 * {@link InstructorsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link InstructorsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InstructorsFragment extends Fragment implements
        SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = InstructorsFragment.class.getSimpleName();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private List<Instructor> instructorList = new ArrayList<>();
    private RecyclerView recycler;
    private InstructorsAdapter iAdapter;

    private OnFragmentInteractionListener mListener;
    private ProgressBar mProgressBar;
    private SwipeRefreshLayout mySwipeRefreshLayout;

    private Utils utils;

    public InstructorsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InstructorsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InstructorsFragment newInstance(String param1, String param2) {
        InstructorsFragment fragment = new InstructorsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        utils = new Utils(getActivity());
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_instructors, container, false);

        view.findViewById(R.id.tv_no_instructors).setVisibility(View.VISIBLE);

        mySwipeRefreshLayout = view.findViewById(R.id.instructorsswiperrefresh);
        mySwipeRefreshLayout.setOnRefreshListener(this);

        mySwipeRefreshLayout.setRefreshing(true);
        recycler = view.findViewById(R.id.instructorsRecycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        recycler.setLayoutManager(layoutManager);

        iAdapter = new InstructorsAdapter(getContext(), instructorList);

        recycler.setAdapter(iAdapter);

        Log.e(TAG, "Created view: Getting data");
        prepareInstructorData();

        return view;
    }

    private void prepareInstructorData() {
        Log.e(TAG, "Preparing instructor data");

        if (!mySwipeRefreshLayout.isRefreshing())
            mySwipeRefreshLayout.setRefreshing(true);

        if (utils.isInternetConnected()) {

            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

            Call<InstructorResponse> call = apiInterface.getInstructors();

            call.enqueue(new Callback<InstructorResponse>() {
                @Override
                public void onResponse(Call<InstructorResponse> call, Response<InstructorResponse> response) {
                    Log.e(TAG, "Response gotten");
                    setInstructorData(response.body().getInstructors());
                }

                @Override
                public void onFailure(Call<InstructorResponse> call, Throwable t) {
                    Log.e(TAG, "Failed " + t.toString());

                    mySwipeRefreshLayout.setRefreshing(false);

                }
            });

        } else {
            utils.toastMessage("No internet connected");
            mySwipeRefreshLayout.setRefreshing(false);
        }
    }

    private void setInstructorData(List<Instructor> instructors) {

        Log.e(TAG, "Setting instructor data");
        if (instructors != null) {
            instructorList.clear();
            instructorList.addAll(instructors);
        }
        else {
            Toast.makeText(getContext(), "Couldn't get instructors...", Toast.LENGTH_SHORT).show();

            getView().findViewById(R.id.tv_no_instructors).setVisibility(View.GONE);
        }

        iAdapter.notifyDataSetChanged();

        mySwipeRefreshLayout.setRefreshing(false);
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

    @Override
    public void onRefresh() {
        prepareInstructorData();
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
