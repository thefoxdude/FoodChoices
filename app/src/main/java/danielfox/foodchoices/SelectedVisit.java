package danielfox.foodchoices;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import java.sql.SQLException;

public class SelectedVisit extends Activity {

    Button back;
    Button edit;
    TextView restaurantName;
    TextView foodEaten;
    TextView date;
    RatingBar rating;
    TextView price;
    RatingBar service;
    TextView comments;
    Long visitID;
    Long userID;
    Visit currentVisit;
    DatabaseHelper database;
    Boolean editOrSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_visit);

        initControls();
    }

    private void initControls() {
        back = (Button) findViewById(R.id.selectedVisitBack);
        edit = (Button) findViewById(R.id.selectedVisitEdit);
        restaurantName = (TextView) findViewById(R.id.visitRestaurantName);
        foodEaten = (TextView) findViewById(R.id.visitFoodEaten);
        date = (TextView) findViewById(R.id.visitDate);
        rating = (RatingBar) findViewById(R.id.visitOverallRating);
        price = (TextView) findViewById(R.id.visitPrice);
        service = (RatingBar) findViewById(R.id.visitServiceRating);
        comments = (TextView) findViewById(R.id.visitComments);
        visitID = getIntent().getExtras().getLong("id");
        userID = getIntent().getExtras().getLong("userID");
        database = DatabaseHelper.getInstance(getApplicationContext());
        currentVisit = database.getSelectedVisit(visitID);
        restaurantName.setText(currentVisit.getRestaurant());
        foodEaten.setText(currentVisit.getFood());
        date.setText(currentVisit.getDate());
        rating.setRating(currentVisit.getStars());
        price.setText(String.valueOf(currentVisit.getPrice()));
        service.setRating(currentVisit.getService());
        comments.setText(currentVisit.getComments());
        editOrSave = true;

        try {
            database.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editOrSave) {
                    Intent startNewActivity = new Intent(getApplicationContext(), RestaurantList.class);
                    startNewActivity.putExtra("userID", userID);
                    startNewActivity.putExtra("restaurantName", restaurantName.getText().toString());
                    startActivity(startNewActivity);
                    finish();
                }
                else {
                    restaurantName.setEnabled(false);
                    foodEaten.setEnabled(false);
                    date.setEnabled(false);
                    rating.setIsIndicator(true);
                    price.setEnabled(false);
                    service.setIsIndicator(true);
                    comments.setEnabled(false);
                    back.setText("Back");
                    edit.setText("Edit");
                    editOrSave = true;
                }
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editOrSave) {
                    restaurantName.setEnabled(true);
                    foodEaten.setEnabled(true);
                    date.setEnabled(true);
                    rating.setIsIndicator(false);
                    price.setEnabled(true);
                    service.setIsIndicator(false);
                    comments.setEnabled(true);
                    back.setText("Cancel");
                    edit.setText("Save");
                    editOrSave = false;
                }
                else {
                    restaurantName.setEnabled(false);
                    foodEaten.setEnabled(false);
                    date.setEnabled(false);
                    rating.setIsIndicator(true);
                    price.setEnabled(false);
                    service.setIsIndicator(true);
                    comments.setEnabled(false);
                    back.setText("Back");
                    edit.setText("Edit");
                    editOrSave = true;
                    // Save to database
                    database.deleteVisit(visitID);
                    database.createVisit(userID, restaurantName.getText().toString(),
                            foodEaten.getText().toString(),
                            date.getText().toString(),
                            (int) rating.getRating(),
                            Double.parseDouble(price.getText().toString()),
                            (int) service.getRating(),
                            comments.getText().toString());
                }
            }
        });
    }


}
