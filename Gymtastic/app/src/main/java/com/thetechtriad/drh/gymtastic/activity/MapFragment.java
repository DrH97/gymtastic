package com.thetechtriad.drh.gymtastic.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.thetechtriad.drh.gymtastic.R;
import com.thetechtriad.drh.gymtastic.Utils;
import com.thetechtriad.drh.gymtastic.model.Gym;
import com.thetechtriad.drh.gymtastic.model.GymResponse;
import com.thetechtriad.drh.gymtastic.model.UserResponse;
import com.thetechtriad.drh.gymtastic.rest.ApiClient;
import com.thetechtriad.drh.gymtastic.rest.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MapFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment implements
        OnMapReadyCallback,
        SwipeRefreshLayout.OnRefreshListener,
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        GoogleMap.OnInfoWindowClickListener {

    public class MapCustomInfoWindow implements GoogleMap.InfoWindowAdapter {
        private Context context;

        public MapCustomInfoWindow(Context context) {
            this.context = context;
        }

        @Override
        public View getInfoWindow(Marker marker) {
            return null;
        }

        @Override
        public View getInfoContents(Marker marker) {
            View view = ((Activity)context).getLayoutInflater().inflate(R.layout.custom_info_window, null);

            TextView title = view.findViewById(R.id.gym_title);
            TextView open = view.findViewById(R.id.gym_open_time);
            TextView close = view.findViewById(R.id.gym_close_time);

            String[] times = marker.getSnippet().split("-");
            title.setText(marker.getTitle());
            open.setText(times[0].trim());
            close.setText(times[1].trim());

            return view;
        }
    }

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

    private LocationManager locationManager;
    private Location myLocation;
    private Criteria criteria;
    private String mProvider;
    private LocationListener mLocationListener;

    private static final int ONE_MIN = 1000 * 60;
    private static final int TWO_MIN = 1000 * 60 * 2;

    private Utils utils;

    private int userGymLocation;

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

        utils = new Utils(getActivity());

        userGymLocation = utils.getUserLocation(getContext());

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

        mProgressBar = view.findViewById(R.id.pbMapFragment);
        showLoader(true);

        mySwipeRefreshLayout = view.findViewById(R.id.mapsswiperrefresh);
        mySwipeRefreshLayout.setOnRefreshListener(this);

        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        mProvider = LocationManager.NETWORK_PROVIDER;

        locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);

        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (isBetterLocation(location, myLocation)) {
//                    utils.toastMessage("OnLocationChanged");
                    myLocation = location;
                    setLocation();
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
//                utils.toastMessage("OnStatusChanged");
                mProvider = provider;
                setLocation();
            }

            @Override
            public void onProviderEnabled(String provider) {
                mProvider = provider;
                setLocation();

            }

            @Override
            public void onProviderDisabled(String provider) {
                mProvider = LocationManager.NETWORK_PROVIDER;
                setLocation();
            }
        };

        if (mMap == null) {
            SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this); //it'll start map service, getMapAsync(), чтобы установить обратный вызов для фрагмента
        }

    }

    private void setLocation() {
        if (!checkPermissions())
            requestPermissions();
    }

    protected boolean checkPermissions() {
        if (getContext() != null)
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return false;
            } else {
                locationManager.requestLocationUpdates(mProvider, 0, 0, mLocationListener);
                myLocation = locationManager.getLastKnownLocation(mProvider);

                mMap.setOnMyLocationButtonClickListener(this);
                mMap.setOnMyLocationClickListener(this);
                setLocationControls();
            }
        return true;
    }

    private void setLocationControls() {
        if (myLocation != null) {
            mMap.setMyLocationEnabled(true);
        } else {
            mMap.setMyLocationEnabled(false);
        }
    }

    protected void requestPermissions() {
        requestPermissions(new String[]{ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
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

        MapCustomInfoWindow mapCustomInfoWindow = new MapCustomInfoWindow(getContext());
        mMap.setInfoWindowAdapter(mapCustomInfoWindow);
        setDefaultMap();

        mMap.setOnInfoWindowClickListener(this);
        if (!checkPermissions())
            requestPermissions();

        if (isInternetConnected())
            getGymLocations();
        else
            utils.toastMessage("No internet, Please connect and try again...");

//        locationManager.removeUpdates(mLocationListener);
        showLoader(false);
    }

    protected boolean isBetterLocation(Location location, Location currentBestLocation) {
        if (currentBestLocation == null)
            return true;

        long timeDelta = location.getTime() - currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > TWO_MIN;
        boolean isSignificantlyOlder = timeDelta < -TWO_MIN;
        boolean isNewer = timeDelta > 0;

        if (isSignificantlyNewer)
            return true;
        else if (isSignificantlyOlder)
            return false;

        int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;

        boolean isFromSameProvider = isSameProvider(location.getProvider(), currentBestLocation.getProvider());

        if (isMoreAccurate)
            return true;
        else if (isNewer && !isLessAccurate)
            return true;
        else if (isNewer && !isSignificantlyLessAccurate && !isFromSameProvider)
            return true;

        return false;
    }

    private boolean isSameProvider(String provider1, String provider2) {
        if (provider1 == null)
            return provider2 == null;

        return provider1.equals(provider2);
    }

    protected boolean isInternetConnected() {
        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public void setDefaultMap() {
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(AFRICA, 0));

//        mMap.setLatLngBoundsForCameraTarget(AFRICABOUNDS);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkPermissions();
            } else {
                utils.toastMessage("Location permissions were denied...");
            }
        }
    }

    private void getGymLocations() {
        mySwipeRefreshLayout.setRefreshing(true);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<GymResponse> call = apiInterface.getGyms();

        call.enqueue(new Callback<GymResponse>() {
            @Override
            public void onResponse(Call<GymResponse> call, Response<GymResponse> response) {
                if (response.body() != null) {
                    gyms = response.body().getGyms();
                    addGymMapMarkers(gyms);
                    ((MainActivity) getActivity()).setGymLocations(gyms);
                }
            }

            @Override
            public void onFailure(Call<GymResponse> call, Throwable t) {

            }
        });
    }

    private void addGymMapMarkers(List<Gym> gyms) {

        if (gyms != null)
            for (Gym gym : gyms) {
                MarkerOptions markerOptions = new MarkerOptions();
                if (userGymLocation != 0 && gym.getId() == userGymLocation) {
                    markerOptions.position(new LatLng(gym.getLatitude(), gym.getLongitude()))
                            .title(gym.getName())
                            .snippet(gym.getOpening_time() + "\t- \t" + gym.getClosing_time())
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                } else {
                    markerOptions.position(new LatLng(gym.getLatitude(), gym.getLongitude()))
                            .title(gym.getName())
                            .snippet(gym.getOpening_time() + "\t- \t" + gym.getClosing_time());
                }

                Marker m = mMap.addMarker(markerOptions);
                m.setTag(gym.getId());
//                m.showInfoWindow();
            }
        else
            Toast.makeText(getContext(), "No gyms gotten yet...", Toast.LENGTH_SHORT).show();

        showLoader(false);
        mySwipeRefreshLayout.setRefreshing(false);
    }

    private void getNearGymLocations() {
        if (gyms != null) {
            List<Gym> closeGyms = new ArrayList<>();
            float[] results = new float[1];

            for (Gym gym : gyms) {
                Location.distanceBetween(myLocation.getLatitude(), myLocation.getLongitude(), gym.getLatitude(), gym.getLongitude(), results);

                if (results[0] < 50000) {
                    closeGyms.add(gym);
                }
            }

            if (closeGyms != null)
                addGymMapMarkers(closeGyms);
            else
                Toast.makeText(getContext(), "No gyms near you", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRefresh() {
        setLocation();
        if (utils.isInternetConnected())
            if (zoomedToLocation)
                if (myLocation != null)
                    getNearGymLocations();
                else {
                    setDefaultMap();
                    addGymMapMarkers(gyms);
                    utils.toastMessage("Location has been disabled");
                }
            else {
                getGymLocations();
                addGymMapMarkers(gyms);
                setDefaultMap();
            }
        else {
            utils.toastMessage("No internet Connection");
            mySwipeRefreshLayout.setRefreshing(false);
        }

    }

    @Override
    public boolean onMyLocationButtonClick() {
        if (!zoomedToLocation) {

            Log.e("MF", "Zooming in.");

            if (myLocation != null) {

                Log.e("MF", myLocation.toString());

                mMap.clear();

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(myLocation.getLatitude(), myLocation.getLongitude()), (float) 10.64));

                getNearGymLocations();

                zoomedToLocation = true;
            } else {
                Snackbar.make(getView().findViewById(R.id.mapsswiperrefresh), "Could not get location, please make sure location is enabled and try again...", Snackbar.LENGTH_LONG).show();
            }

            return true;
        }
        else {

            Log.e("MF", "Zooming out");
            zoomedToLocation = false;
            setDefaultMap();
            addGymMapMarkers(gyms);
            return true;
        }

    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {

    }

    @Override
    public void onInfoWindowClick(final Marker marker) {
        if (Integer.parseInt(marker.getTag().toString()) != userGymLocation)
            Snackbar.make(getView(), "Set this as default Gym?", Snackbar.LENGTH_SHORT)
                    .setAction("YES", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mySwipeRefreshLayout.setRefreshing(true);
                            setDefaultLoc(marker.getTag().toString(), marker);
                        }
                    }).setActionTextColor(Color.CYAN).show();
//        utils.toastMessage(marker.getTag().toString());

    }

    private void setDefaultLoc(String s, Marker marker) {
        int userId = Utils.getUserId(getContext());
        final int locationId = Integer.parseInt(s);

        marker.hideInfoWindow();

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<UserResponse> call = apiInterface.addUserLocation(userId, locationId);

        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.body().getStatus().equals("success")) {
                    utils.setUserLocation(getContext(), locationId);
                    utils.toastMessage(response.body().getMessage());
//                    utils.toastMessage("Location set successfully");
                    userGymLocation = utils.getUserLocation(getContext());
                    onRefresh();
                } else {
                    utils.toastMessage(response.body().getStatus());

                    mySwipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                utils.toastMessage(t.toString());

                mySwipeRefreshLayout.setRefreshing(false);
            }
        });

    }

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
