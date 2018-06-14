package com.thetechtriad.drh.gymtastic.activity;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {
    final static String GYMTASTIC_BASE_URL =
            "http://gymtastic-api.herokuapp.com/api/v1";

    /**
     * Builds the URL used to query GitHub.
     *
     * @param gymtasticSearchQuery The keyword that will be queried for.
     * @return The URL to use to query Gymtastic.
     */
    public static URL buildUrl(String gymtasticSearchQuery, String... params) {
        Uri builtUri = Uri.parse(GYMTASTIC_BASE_URL).buildUpon()
                .appendQueryParameter("/", gymtasticSearchQuery)
                .appendQueryParameter("email", params[0])
                .appendQueryParameter("password", params[1])
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
