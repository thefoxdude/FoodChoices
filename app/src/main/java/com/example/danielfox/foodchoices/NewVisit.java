package com.example.danielfox.foodchoices;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import java.sql.SQLException;

public class NewVisit extends Activity {

    Button cancel;
    Button save;
    Long userID;
    EditText restaurantInput;
    String restaurant;
    EditText foodInput;
    String food;
    EditText dateInput;
    String date;
    RatingBar overallRating;
    int overall;
    EditText priceInput;
    double price;
    RatingBar serviceRating;
    int service;
    EditText commentsInput;
    String comments;
    DatabaseHelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_visit);

        initControls();
    }

    public void initControls() {
        cancel = (Button) (findViewById(R.id.selectedVisitBack));
        save = (Button) (findViewById(R.id.selectedVisitEdit));
        database = DatabaseHelper.getInstance(this);
        userID = getIntent().getExtras().getLong("userID");
        try {
            database.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startNewActivity = new Intent(getApplicationContext(), HomePage.class);
                startNewActivity.putExtra("name", getIntent().getExtras().getString("name"));
                startNewActivity.putExtra("userID", getIntent().getExtras().getLong("userID"));
                startActivity(startNewActivity);
                finish();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restaurantInput = (EditText) (findViewById(R.id.visitRestaurantName));
                restaurant = restaurantInput.getText().toString();
                foodInput = (EditText) (findViewById(R.id.visitFoodEaten));
                food = foodInput.getText().toString();
                dateInput = (EditText) (findViewById(R.id.visitDate));
                date = dateInput.getText().toString();
                overallRating = (RatingBar) (findViewById(R.id.visitOverallRating));
                overall = (int) overallRating.getRating();
                priceInput = (EditText) (findViewById(R.id.visitPrice));
                price = Double.parseDouble(priceInput.getText().toString());
                serviceRating = (RatingBar) (findViewById(R.id.visitServiceRating));
                service = (int) serviceRating.getRating();
                commentsInput = (EditText) (findViewById(R.id.comments));
                comments = commentsInput.getText().toString();
                database.createVisit(userID, restaurant, food, date, overall, price, service, comments);
                Intent startNewActivity = new Intent(getApplicationContext(), HomePage.class);
                startNewActivity.putExtra("name", getIntent().getExtras().getString("name"));
                startNewActivity.putExtra("userID", userID);
                startActivity(startNewActivity);
                finish();
            }
        });
    }


}
