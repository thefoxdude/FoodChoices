package com.example.danielfox.foodchoices;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.sql.SQLException;

public class RestaurantList extends Activity {

    String restaurantName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);

        initControls();


    }

    private void initControls() {
        restaurantName = getIntent().getExtras().getString("name");
        TextView header = (TextView) findViewById(R.id.restaurantHeader);
        header.setText(restaurantName);

        DatabaseHelper database = DatabaseHelper.getInstance(getApplicationContext());
        try {
            database.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

}
