package com.vc19003337.myvcweatherapp;

import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonSerializer;

import java.util.List;

import model.DailyForecasts;

/**
 * TODO: Replace the implementation with code for your data type.
 */
public class MyDailyForecastsRecyclerViewAdapter extends RecyclerView.Adapter<MyDailyForecastsRecyclerViewAdapter.ViewHolder>
{
    private final List<DailyForecasts> mValues;

    public MyDailyForecastsRecyclerViewAdapter(List<DailyForecasts> items)
    {
        mValues = items;
        //Log.i("MyDailyAdapter", "Items: " + items.toString());
        Log.i("MyDailyAdapter", "mValues: " + mValues.toString());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);

        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position)
    {
        DailyForecasts item = mValues.get(position);
        holder.mItem = item;
        Log.i("onBindViewHolder", "holder item set");
        holder.tvDate.setText(item.getDate().substring(0,10));
        Log.i("onBindViewHolder", "holder date set");
        holder.tvMinimum.setText(item.getTemperature().getMinimum().getValue() +
                " " + item.getTemperature().getMinimum().getUnit());
        Log.i("onBindViewHolder", "holder min set");
        holder.tvMaximum.setText(item.getTemperature().getMaximum().getValue() +
                " " + item.getTemperature().getMaximum().getUnit());
        Log.i("onBindViewHolder", "holder max set");
    }

    @Override
    public int getItemCount()
    {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public final View mView;
        public final TextView tvDate;
        public final TextView tvMinimum;
        public final TextView tvMaximum;
        public DailyForecasts mItem;

        public ViewHolder(View view)
        {
            super(view);
            mView = view;
            tvDate = (TextView) view.findViewById(R.id.tv_date);
            tvMinimum = (TextView) view.findViewById(R.id.tv_minimum);
            tvMaximum = (TextView) view.findViewById(R.id.tv_maximum);
            Log.i("ViewHolder", "layout set");
        }

        @Override
        public String toString()
        {
            return super.toString() + " '" + tvDate.getText() + "'";
        }
    }
}