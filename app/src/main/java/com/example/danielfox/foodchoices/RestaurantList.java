package com.example.danielfox.foodchoices;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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
    String username;
    Boolean editOrSave;
    Button back;
    Button edit;
    List<Long> visitIDs;
    ListView visitsList;
    DatabaseHelper database;


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
        back = (Button) findViewById(R.id.backButton);
        edit = (Button) findViewById(R.id.visitEditButton);
        header.setText(restaurantName);
        username = getIntent().getExtras().getString("name");
        editOrSave = true;
        visitIDs = new ArrayList<>();

        database = DatabaseHelper.getInstance(getApplicationContext());
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
                if (editOrSave) {
                    Intent intent = new Intent(getApplicationContext(), SelectedVisit.class);
                    intent.putExtra("username", username);
                    intent.putExtra("id", currentVisit.getVisitID());
                    startActivity(intent);
                    onPause();
                } else {
                    if (currentVisit.getSelected() == 0) {
                        visitIDs.add(currentVisit.getVisitID());
                        currentVisit.setSelected(1);
                        parent.getChildAt(position).setBackgroundColor(Color.rgb(0, 153, 76));
                    } else {
                        visitIDs.remove(currentVisit.getVisitID());
                        currentVisit.setSelected(0);
                        parent.getChildAt(position).setBackgroundColor(getResources().getColor(R.color.background));
                    }
                }
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editOrSave) {
                    editOrSave = false;
                    edit.setText("Delete");
                    back.setText("Cancel");
                } else {
                    new AlertDialog.Builder(RestaurantList.this).setTitle("Really Delete?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    editOrSave = true;
                                    edit.setText("Edit");
                                    back.setText("Back");
                                    for (Long deleteVisit : visitIDs) {
                                        database.deleteVisit(deleteVisit);
                                    }
                                    Intent intent = getIntent();
                                    finish();
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();

                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editOrSave) {
                    Intent startNewActivity = new Intent(getApplicationContext(), HomePage.class);
                    startNewActivity.putExtra("name", getIntent().getExtras().getString("name"));
                    startActivity(startNewActivity);
                    finish();
                } else {
                    editOrSave = true;
                    edit.setText("Edit");
                    back.setText("Back");
                    visitIDs = new ArrayList<>();
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }
            }
        });

    }

}
