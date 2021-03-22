package com.vc19003337.myvcweatherapp;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URL;

import model.Root;

/**
 * A fragment representing a list of Items.
 */
public class DailyForecastsFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private RecyclerView weatherDataList;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public DailyForecastsFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static DailyForecastsFragment newInstance(int columnCount) {
        DailyForecastsFragment fragment = new DailyForecastsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }


    /**
     * Asynchronous task that requests weather data.
     */
    class FetchWeatherData extends AsyncTask<URL, Void, String>
    {
        //private static final String LOGGING_TAG = "weatherDATA";

        @Override
        protected String doInBackground(URL... urls)
        {
            URL weatherURL = urls[0];
            String weatherData = null;
            Log.i("Checking weatherURL" , " " + weatherURL.toString());

            try
            {
                weatherData = NetworkUtil.getResponseFromHttpUrl(weatherURL);
                Log.i("Checking weather data" , " " + weatherData);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            return weatherData;
        }

        protected void consumeJson(String weatherJSON)
        {
            if (weatherJSON != null)
            {
                Gson gson = new Gson();
                Root weatherData = gson.fromJson(weatherJSON, Root.class);
                //Log.i("Checking the object - ", "" + weatherData.getDailyForecasts().toString());
                weatherDataList.setAdapter(new MyDailyForecastsRecyclerViewAdapter(weatherData.getDailyForecasts()));
                Log.i("consumeJson method", "Adapter set!");
            }
        }

        @Override
        protected void onPostExecute(String weatherData)
        {
            if (weatherData != null)
            {
                //tvWeather.setText(weatherData);
                consumeJson(weatherData);
                Log.i("consumeJson call", "Got here!");
                //Log.i("onPostExecute", " " + weatherData.toString());
            }
            super.onPostExecute(weatherData);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView)
        {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1)
            {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            }
            else
                {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            weatherDataList = recyclerView;
            URL url = NetworkUtil.buildURLForWeather();
            new FetchWeatherData().execute(url);
            //recyclerView.setAdapter(new MyDailyForecastsRecyclerViewAdapter(DummyContent.ITEMS));
        }
        return view;
    }
}