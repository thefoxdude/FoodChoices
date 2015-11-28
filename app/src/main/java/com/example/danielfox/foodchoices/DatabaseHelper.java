package com.example.danielfox.foodchoices;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "Database:";
    private static DatabaseHelper dbHelper = null;
    private SQLiteDatabase database = null;

    private static final String DATABASE_NAME = "foodchoices.db";
    private static final int DATABASE_VERSION = 1;

    public static synchronized DatabaseHelper getInstance(Context context) {
        if(dbHelper == null) {
            dbHelper = new DatabaseHelper(context.getApplicationContext());
        }
        return dbHelper;
    }

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void open() throws SQLException {
        database = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL("CREATE TABLE IF NOT EXISTS USER(" +
                "userID INTEGER PRIMARY KEY, " +
                "firstName TEXT, " +
                "lastName TEXT, " +
                "username TEXT, " +
                "password TEXT);");
        database.execSQL("CREATE TABLE IF NOT EXISTS VISIT(" +
                "visitID INTEGER PRIMARY KEY, " +
                "userID INTEGER," +
                "restaurant TEXT, " +
                "food TEXT, " +
                "date TEXT, " +
                "stars INTEGER, " +
                "price DOUBLE, " +
                "service INTEGER, " +
                "comments TEXT, " +
                "selected INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        Log.i(TAG, "Upgrading database from version " + oldVersion + " to "
                + newVersion);
        database.execSQL("DROP TABLE IF EXISTS USER");
        database.execSQL("DROP TABLE IF EXISTS VISIT");
        onCreate(database);
    }

    public void deleteUser (long id) {
        database.delete("USER", "userID = " + id, null);
    }

    public User createUser(String firstName, String lastName, String username, String password) {
        ContentValues values = new ContentValues();
        values.put("firstName", firstName);
        values.put("lastName", lastName);
        values.put("username", username);
        values.put("password", password);

        long insertId = database.insert("USER", null, values);

        if (insertId != -1) {
            return new User(insertId,firstName, lastName, username, password);
        }

        Log.e(TAG, "Error inserting data!");
        return null;
    }

    public User findUser(String username, String password) {
        User currentUser = new User();
        Cursor cursor = database.rawQuery("select * from USER WHERE username ='" + username + "' AND password = '" + password + "'", null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            currentUser.setUserID(cursor.getLong(0));
            currentUser.setFirstName(cursor.getString(1));
            currentUser.setLastName(cursor.getString(2));
            currentUser.setPassword(cursor.getString(3));
            cursor.moveToNext();
        }
        return currentUser;
    }

    public User findUser(Long userID) {
        User currentUser = new User();
        Cursor cursor = database.rawQuery("select * from USER WHERE userID = " + userID, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            currentUser.setUserID(cursor.getLong(0));
            currentUser.setFirstName(cursor.getString(1));
            currentUser.setLastName(cursor.getString(2));
            currentUser.setPassword(cursor.getString(3));
            cursor.moveToNext();
        }
        return currentUser;
    }

    public void deleteVisit (long id) {
        database.delete("VISIT", "visitID = " + id, null);
    }

    public Visit createVisit(long userID, String restaurant, String food, String date, int stars, double price, int service, String comments) {
        int selected = 0;
        ContentValues values = new ContentValues();
        values.put("userID", userID);
        values.put("restaurant", restaurant);
        values.put("food", food);
        values.put("date", date);
        values.put("stars", stars);
        values.put("price", price);
        values.put("service", service);
        values.put("comments", comments);
        values.put("selected", selected);

        long insertId = database.insert("VISIT", null, values);

        if (insertId != -1) {
            return new Visit(insertId, userID, restaurant, food, date, stars, price, service, comments, selected);
        }

        Log.e(TAG, "Error inserting dat!");
        return null;
    }

    public List<Visit> getAllVisits() {
        List<Visit> visits = new ArrayList<>();

        Cursor cursor = database.rawQuery("select * from VISIT", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Visit visit = new Visit();
            visit.setVisitID(cursor.getLong(0));
            visit.setUserID(cursor.getLong(1));
            visit.setRestaurant(cursor.getString(2));
            visit.setFood(cursor.getString(3));
            visit.setDate(cursor.getString(4));
            visit.setStars(cursor.getInt(5));
            visit.setPrice(cursor.getDouble(6));
            visit.setService(cursor.getInt(7));
            visit.setComments(cursor.getString(8));
            visit.setSelected(cursor.getInt(9));
            visits.add(visit);
            cursor.moveToNext();
        }

        cursor.close();
        return visits;
    }

    public List<Visit> getVisits(String restaurantName) {
        List<Visit> visits = new ArrayList<>();
        Cursor cursor = database.rawQuery("select * from VISIT where restaurant='" + restaurantName + "'", null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            Visit visit = new Visit();
            visit.setVisitID(cursor.getLong(0));
            visit.setUserID(cursor.getLong(1));
            visit.setRestaurant(cursor.getString(2));
            visit.setFood(cursor.getString(3));
            visit.setDate(cursor.getString(4));
            visit.setStars(cursor.getInt(5));
            visit.setPrice(cursor.getDouble(6));
            visit.setService(cursor.getInt(7));
            visit.setComments(cursor.getString(8));
            visit.setSelected(cursor.getInt(9));
            visits.add(visit);
            cursor.moveToNext();
        }
        cursor.close();
        return visits;
    }

    public Visit getSelectedVisit(Long visitID) {
        Visit visit = new Visit();
        Cursor cursor = database.rawQuery("select * from VISIT where visitID = " + visitID, null);
        cursor.moveToFirst();
        visit.setVisitID(cursor.getLong(0));
        visit.setUserID(cursor.getLong(1));
        visit.setRestaurant(cursor.getString(2));
        visit.setFood(cursor.getString(3));
        visit.setDate(cursor.getString(4));
        visit.setStars(cursor.getInt(5));
        visit.setPrice(cursor.getDouble(6));
        visit.setService(cursor.getInt(7));
        visit.setComments(cursor.getString(8));
        visit.setSelected(cursor.getInt(9));
        return visit;
    }


}