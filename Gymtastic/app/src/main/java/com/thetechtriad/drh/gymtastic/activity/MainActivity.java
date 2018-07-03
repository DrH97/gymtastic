package com.thetechtriad.drh.gymtastic.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.thetechtriad.drh.gymtastic.PrefUtil;
import com.thetechtriad.drh.gymtastic.R;
import com.thetechtriad.drh.gymtastic.model.Gym;
import com.thetechtriad.drh.gymtastic.model.WorkoutResponse;
import com.thetechtriad.drh.gymtastic.rest.ApiClient;
import com.thetechtriad.drh.gymtastic.rest.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MapFragment.OnFragmentInteractionListener, WorkoutsFragment.OnFragmentInteractionListener, AdapterView.OnItemSelectedListener, InstructorsFragment.OnFragmentInteractionListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private FloatingActionButton fab;
    private ViewPager mViewPager;
    private Toolbar toolbar;

    public int userId;
    public static int navItemIndex = 0;
    public ProgressBar mProgressView;

    private static final String TAG_HOME = "home";
    public static String CURRENT_TAG = TAG_HOME;

    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;

    public Dialog workoutDialog;
    ArrayList<Integer> spinnerArrayIds;
    private Integer selected_location = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressView = findViewById(R.id.login_progress);

        showProgress(true);
        SharedPreferences sharedPrefs = getApplication().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        userId = sharedPrefs.getInt("userId", 0);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        mHandler = new Handler();

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(3);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        workoutDialog = new Dialog(MainActivity.this, R.style.Theme_AppCompat_NoActionBar);
        workoutDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100,0,0,0)));
        workoutDialog.setContentView(R.layout.fragment_add_workout);
        workoutDialog.setCancelable(true);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                workoutDialog.show();
            }
        });

        loadNavHeader();

        setUpNavigationView();


    }

    private void loadNavHeader() {

    }

    private void loadHomeFragment() {

    }

    private void setUpNavigationView() {

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return false;
            }
        });

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        drawer.setDrawerListener(actionBarDrawerToggle);

        actionBarDrawerToggle.syncState();

        showProgress(false);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }

        if (shouldLoadHomeFragOnBackPress) {
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                loadHomeFragment();
                return;
            }
        }

        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_refresh) {
            WorkoutsFragment.newInstance().prepareWorkoutData();
//            WorkoutsFragment workoutsFragment = (WorkoutsFragment) getSupportFragmentManager().findFragmentById(R.id.workoutsFragment);
//            workoutsFragment.prepareWorkoutData();
        }

        if (id == R.id.action_logout) {
            PrefUtil prefUtil = new PrefUtil(this);
            prefUtil.logoutUser();

            startActivity(new Intent(this, SplashScreenActivity.class));
            finish();
        }

        if (id == R.id.action_profile) {

            startActivity(new Intent(this, ProfileActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }

    public void addNewWorkout(View view) {
        EditText exercise = workoutDialog.findViewById(R.id.et_exercise);
        EditText reps = workoutDialog.findViewById(R.id.et_reps);
        EditText sets = workoutDialog.findViewById(R.id.et_sets);
        EditText date = workoutDialog.findViewById(R.id.et_date);
//        Spinner spinner = workoutDialog.findViewById(R.id.spinner);
        String exercise_text = null, date_text = null;
        int reps_text = 0, sets_text = 0;

        if (!(exercise.getText().toString()).isEmpty())
            exercise_text = exercise.getText().toString();
        if (!(reps.getText().toString()).isEmpty())
            reps_text = Integer.valueOf(reps.getText().toString());
        if (!(sets.getText().toString()).isEmpty())
            sets_text = Integer.valueOf(sets.getText().toString());
        if (!(date.getText().toString()).isEmpty())
            date_text = date.getText().toString();

        Log.e(TAG, selected_location + ", " + date_text + ", " + exercise_text + ", " +reps_text + ", " +sets_text + "");

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<WorkoutResponse> call = apiInterface.addWorkout(2, selected_location, date_text, exercise_text, reps_text, sets_text);

        call.enqueue(new Callback<WorkoutResponse>() {
            @Override
            public void onResponse(Call<WorkoutResponse> call, Response<WorkoutResponse> response) {
//                Log.e(TAG, response.body().getWorkout_date()[0]);
//                Log.e(TAG, response.body().getWorkouts().get(0).getExerciseType());
            }

            @Override
            public void onFailure(Call<WorkoutResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        
        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    MapFragment tab1 = new MapFragment();
                    return tab1;
                case 1:
                    WorkoutsFragment tab2 = new WorkoutsFragment();
                    return tab2;
                case 2:
                    InstructorsFragment tab3 = new InstructorsFragment();
                    return tab3;
                default:
                    return PlaceholderFragment.newInstance(position + 1);
            }


        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

//                mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
//                mLoginFormView.animate().setDuration(shortAnimTime).alpha(
//                        show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
//                    }
//                });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
//                mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    public void setGymLocations(List<Gym> gyms) {
        Spinner spinner = workoutDialog.findViewById(R.id.spinner);
        ArrayList<String> spinnerArray = new ArrayList<>();
        spinnerArrayIds = new ArrayList<>();

        for (Gym gym: gyms) {
            spinnerArrayIds.add(gym.getId());
            spinnerArray.add(gym.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_location = spinnerArrayIds.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}
