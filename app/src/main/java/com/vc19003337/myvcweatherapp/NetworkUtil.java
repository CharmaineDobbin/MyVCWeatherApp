package com.vc19003337.myvcweatherapp;

import android.net.Uri;
import android.util.Log;

import com.google.gson.JsonSerializer;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

import model.DailyForecasts;

/**
 * * Utility class that handles the network connections to AccuWeather.
 * */

public class NetworkUtil
{
    private static final String WEATHERBASE_URL = "https://dataservice.accuweather.com/forecasts/v1/daily/5day/305605";
    private static final String PARAM_METRIC ="metric";
    private static final String METRIC_VALUE = "true";
    private static final String PARAM_API_KEY = "apikey";
    private static final String LOGGING_TAG = "URLWECREATED";

    private NetworkUtil()
    {

    }

    /**
     * Creates the URL to get the 5-day forecast for Durban, in metric units of measurement.
     * @return The URL for the service.
     */
    public static URL buildURLForWeather()
    {
        Uri buildUri = Uri.parse(WEATHERBASE_URL).buildUpon()
                .appendQueryParameter(PARAM_API_KEY,
                        BuildConfig.ACCUWEATHER_API_KEY) // passing in api key//
                .appendQueryParameter(PARAM_METRIC, METRIC_VALUE) // passing in metric as measurement unit
                .build();

        URL url = null;

        try
        {
            url = new URL(buildUri.toString());
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }

        Log.i(LOGGING_TAG, "buildURLForWeather: " + url);

        return url;
    }

    // Method to create a connection to a URL and reads whatever data is available from the service
    public static String getResponseFromHttpUrl(URL url) throws IOException
    {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try
        {
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("//A");
            boolean hasInput = scanner.hasNext();

            if (hasInput)
            {
                String s = scanner.next();
                Log.i("Check getResponseHTTP", "" + s);
                return s;
            }
            else {
                Log.i("Checking data", "error");
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
