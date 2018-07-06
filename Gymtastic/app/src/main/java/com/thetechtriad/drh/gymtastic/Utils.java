package com.thetechtriad.drh.gymtastic;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.thetechtriad.drh.gymtastic.activity.MainActivity;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class Utils {
    private Activity activity;
    private static String PREFS_LANGUAGE;

    public Utils(Activity activity) {
        this.activity = activity;
    }

    public boolean isInternetConnected() {
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public void toastMessage(String s) {
        Toast.makeText(activity.getBaseContext(), s, Toast.LENGTH_SHORT).show();
    }

    public static void setLanguage(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        PREFS_LANGUAGE = preferences.getString(context.getString(R.string.pref_language_key ), "");

        Log.e("UTIL", preferences.getAll().toString());
//        if (PREFS_LANGUAGE.equals("1")){
//            PREFS_LANGUAGE = "kiswahili";
//        } else {
//            PREFS_LANGUAGE = "english";
//        }
        Locale locale = new Locale(PREFS_LANGUAGE);

        Configuration configuration = new Configuration();
        configuration.locale = locale;
        context.getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());
    }

    public static String currentLanguage(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

//        if (!preferences.contains(PREFS_LANGUAGE)) {
//            String deviceLanguage = Locale.getDefault().getLanguage();
////            if (Arrays.asList(Constants.languages).contains(deviceLanguage)) {
////                setLanguage(context, deviceLanguage);
////            } else {
////                setLanguage(context, Constants.languages[0]);
////            }
////        }
//        return prefs.getString(PREFS_LANGUAGE, Constants.languages[0]);

        return preferences.getString(context.getString(R.string.pref_language_key), "null");
    }

    public static Map getUserData(Context context) {
        Map <String, String> arrayMap = new HashMap<>();
        SharedPreferences preferences = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        Log.e("Utils", preferences.getAll().toString());
        String username = preferences.getString("username", "Gymtastic App");
        String email = preferences.getString("email", "gymtastic.app.com");

        arrayMap.put("username", username);
        arrayMap.put("email", email);

        return arrayMap;
    }

    public void setUpUserData(MainActivity mainActivity, View header) {
        TextView username = header.findViewById(R.id.nav_header_title);
        TextView email = header.findViewById(R.id.nav_header_subtitle);

        Map userData = getUserData(mainActivity);

        username.setText(userData.get("username").toString());
        email.setText(userData.get("email").toString());
    }

    public static String getUsermail(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        return preferences.getString("email", "gymtastic.app.com");
    }

    public static int getUserId(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        return preferences.getInt("userId", 0);
    }

    public void setUserLocation(Context context, int location) {
        SharedPreferences preferences = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("user_location", location);
        editor.apply();
    }

    public int getUserLocation(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        return preferences.getInt("user_location", 0);
    }
}
