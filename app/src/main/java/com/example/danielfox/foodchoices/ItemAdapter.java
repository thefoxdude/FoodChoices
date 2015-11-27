package com.example.danielfox.foodchoices;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;


public class ItemAdapter extends ArrayAdapter<Restaurants> {

    private ArrayList<Restaurants> restaurants;


    public ItemAdapter(Context context, int textViewResourceId, ArrayList<Restaurants> objects) {
        super(context, textViewResourceId, objects);
        this.restaurants = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.list_restaurant, null);
        }

        Restaurants i = restaurants.get(position);

        if (i != null) {
            TextView name = (TextView) v.findViewById(R.id.name);
            RatingBar service = (RatingBar) v.findViewById(R.id.service);
            float rating = (float) i.getOverallService();

            name.setText(i.getRestaurantName());
            service.setRating(rating);
        }

        return v;
    }
}
