package com.example.danielfox.foodchoices;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HomePage extends Activity {

    Button logout;
    Button newVisit;
    TextView welcome;
    SearchView search;
    Spinner filter;
    Long userID;
    String username;
    List<String> filters = new ArrayList<>();
    ListView restaurantList;
    DatabaseHelper database;
    List<Visit> allRestaurants;
    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        initControls();
    }

    private void initControls() {
        logout = (Button) (findViewById(R.id.logoutButton));
        newVisit = (Button) (findViewById(R.id.newVisitButton));
        search = (SearchView) (findViewById(R.id.search));
        filter = (Spinner) (findViewById(R.id.filter));
        database = DatabaseHelper.getInstance(this);
        userID = getIntent().getExtras().getLong("id");
        welcome = (TextView) (findViewById(R.id.welcomeText));
        filters.add("Name");
        filters.add("Stars");
        ArrayAdapter<String> filterAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, filters);
        filterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filter.setAdapter(filterAdapter);
        try {
            database.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        currentUser = database.findUser(userID);
        username = currentUser.getFirstName();
        welcome.setText("Welcome " + username);
        ArrayList<Restaurants> listOfRestaurants = new ArrayList<>();

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startNewActivity = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(startNewActivity);
                finish();
            }
        });

        newVisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startNewActivity = new Intent(getApplicationContext(), NewVisit.class);
                startNewActivity.putExtra("name", username);
                startActivity(startNewActivity);
                finish();
            }
        });

        restaurantList = (ListView) (findViewById(R.id.restaurantList));
        allRestaurants = database.getAllVisits();
        for (Visit currentRestaurant: allRestaurants) {
            Long id = currentRestaurant.getVisitID();
            String name = currentRestaurant.getRestaurant();
            Integer rating = currentRestaurant.getStars();
            listOfRestaurants.add(new Restaurants(id, name, rating));
        }

        ItemAdapter restaurantsAdapter = new ItemAdapter(this, R.layout.list_restaurant, listOfRestaurants);
        restaurantList.setAdapter(restaurantsAdapter);
        restaurantList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Restaurants currentRestaurant = (Restaurants) restaurantList.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), RestaurantList.class);
                intent.putExtra("name", username);
                intent.putExtra("restaurantName", currentRestaurant.getRestaurantName());
                startActivity(intent);
            }
        });

    }


}
