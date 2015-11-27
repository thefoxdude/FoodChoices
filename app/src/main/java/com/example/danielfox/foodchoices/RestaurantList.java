package com.example.danielfox.foodchoices;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RestaurantList extends Activity {

    String restaurantName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);

        initControls();


    }

    private void initControls() {
        restaurantName = getIntent().getExtras().getString("restaurantName");
        TextView header = (TextView) findViewById(R.id.restaurantHeader);
        List<Visit> restaurantVisits;
        final ListView visitsList;
        Button back = (Button) findViewById(R.id.backButton);
        header.setText(restaurantName);

        DatabaseHelper database = DatabaseHelper.getInstance(getApplicationContext());
        try {
            database.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        restaurantVisits = database.getVisits(restaurantName);
        visitsList = (ListView) findViewById(R.id.allVisits);

        VisitAdapter visitAdapter = new VisitAdapter(this, R.layout.list_visit, restaurantVisits);
        visitsList.setAdapter(visitAdapter);
        visitsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Visit currentVisit = (Visit) visitsList.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), SelectedVisit.class);
                intent.putExtra("id", currentVisit.getVisitID());
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startNewActivity = new Intent(getApplicationContext(), HomePage.class);
                startNewActivity.putExtra("name", getIntent().getExtras().getString("name"));
                startActivity(startNewActivity);
                finish();
            }
        });

    }

}
