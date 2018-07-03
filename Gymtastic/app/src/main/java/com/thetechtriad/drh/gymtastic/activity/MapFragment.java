package com.thetechtriad.drh.gymtastic.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.thetechtriad.drh.gymtastic.R;
import com.thetechtriad.drh.gymtastic.model.Gym;
import com.thetechtriad.drh.gymtastic.model.GymResponse;
import com.thetechtriad.drh.gymtastic.rest.ApiClient;
import com.thetechtriad.drh.gymtastic.rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MapFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback, SwipeRefreshLayout.OnRefreshListener, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener {

    private static final int REQUEST_LOCATION = 0;
    private GoogleMap mMap;

    private ProgressBar mProgressBar;

    private List<Gym> gyms;

    private LatLngBounds AFRICA = new LatLngBounds(
            new LatLng(-40, -20), new LatLng(40, 50));
    private LatLngBounds AFRICABOUNDS = new LatLngBounds(
            new LatLng(0, 17), new LatLng(0, 17));

    private SwipeRefreshLayout mySwipeRefreshLayout;

    private boolean zoomedToLocation = false;
    private LatLng africa;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
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
        return inflater.inflate(R.layout.fragment_map, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        mProgressBar = getView().findViewById(R.id.pbMapFragment);
        showLoader(true);

        mySwipeRefreshLayout = view.findViewById(R.id.mapsswiperrefresh);
        mySwipeRefreshLayout.setOnRefreshListener(this);

        if (mMap == null) {
            SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this); //it'll start map service, getMapAsync(), чтобы установить обратный вызов для фрагмента
        }

        showLoader(false);
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
    public void onMapReady(GoogleMap googleMap) {

        showLoader(true);
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
//        LatLng africa = new LatLng(-8.7832, 34.5085);
//        mMap.addMarker(new MarkerOptions().position(africa).title("Marker in Africa").snippet("Yess"));
//        mMap.addMarker(new MarkerOptions().position(new LatLng(2.124, -2.234)).title("Marker 2 in Africa"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(africa));
        // Create a LatLngBounds that includes Africa.

// Set the camera to the greatest possible zoom level that includes the
// bounds
        setDefaultMap();

        if (ActivityCompat.checkSelfPermission(getContext(), ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION}, REQUEST_LOCATION);
        } else {
            setMapControls();
        }

        getGymLocations();

    }

    public void setDefaultMap() {
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(AFRICA, 0));
        mMap.setLatLngBoundsForCameraTarget(AFRICABOUNDS);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setMapControls();
            } else {
                Toast.makeText(getContext(), "Location permissions were denied...", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setMapControls() {
//        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        mMap.setMyLocationEnabled(true);
//        }
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);


    }

    private void getGymLocations() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<GymResponse> call = apiInterface.getGyms();

        call.enqueue(new Callback<GymResponse>() {
            @Override
            public void onResponse(Call<GymResponse> call, Response<GymResponse> response) {
                gyms = response.body().getGyms();
                addGymMapMarkers(gyms);
                ((MainActivity) getActivity()).setGymLocations(gyms);
            }

            @Override
            public void onFailure(Call<GymResponse> call, Throwable t) {

            }
        });
    }

    private void addGymMapMarkers(List<Gym> gyms) {

        for (Gym gym : gyms) {
            africa = new LatLng(gym.getLatitude(), gym.getLongitude());
            mMap.addMarker(new MarkerOptions().position(africa).title(gym.getName()).snippet(gym.getOpening_time() + "\t- \t" + gym.getClosing_time()));
        }

        showLoader(false);
        mySwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        getGymLocations();
    }

    @Override
    public boolean onMyLocationButtonClick() {
        if (!zoomedToLocation) {

            Log.e("MF", "Zooming in.");
            zoomedToLocation = true;
            float[] results = new float[1];

            mMap.clear();

            for (Gym gym : gyms) {

                Log.e("MF", "Gyms " + gyms.size());
                LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);

                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    Log.e("MF", "Failed to load location");
                    Toast.makeText(getContext(), "Cannot Load gyms next to you, location disabled", Toast.LENGTH_SHORT).show();
                } else {
                    Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                    Log.e("MF", location.toString());

                    Location.distanceBetween(location.getLatitude(), location.getLongitude(), gym.getLatitude(), gym.getLongitude(), results);

                    if (results[0] < 50000) {
                        africa = new LatLng(gym.getLatitude(), gym.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(africa).title(gym.getName()).snippet(gym.getOpening_time() + "\t- \t" + gym.getClosing_time()));
                    }
                }

            }
            return false;
        }
        else {

            Log.e("MF", "Zooming out");
            zoomedToLocation = false;
            setDefaultMap();
            return true;
        }

    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {

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
