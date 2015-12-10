package danielfox.foodchoices;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class VisitAdapter extends ArrayAdapter<Visit> {
    private List<Visit> restaurants;

    public VisitAdapter(Context context, int textViewResourceId, List<Visit> objects) {
        super(context, textViewResourceId, objects);
        this.restaurants = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.list_visit, null);
        }

        Visit i = restaurants.get(position);

        if (i != null) {
            TextView date = (TextView) v.findViewById(R.id.visitDate);
            TextView food = (TextView) v.findViewById(R.id.visitFood);
            RatingBar visitRating = (RatingBar) v.findViewById(R.id.visitRating);
            float rating = (float) i.getStars();

            date.setText(i.getDate());
            food.setText(i.getFood());
            visitRating.setRating(rating);
        }

        return v;
    }

}
