package com.example.danielfox.foodchoices;

public class Visit {

    private long visitID;
    private long userID;
    private String restaurant;
    private String food;
    private String date;
    private int stars;
    private double price;
    private int service;
    private String comments;
    private int selected;

    public Visit() {

    }

    public Visit(long id, long userID, String restaurant, String food, String date, int stars, double price, int service, String comments, int selected) {
        this.visitID = id;
        this.userID = userID;
        this.restaurant = restaurant;
        this.food = food;
        this.date = date;
        this.stars = stars;
        this.price = price;
        this.service = service;
        this.comments = comments;
        this.selected = selected;
    }

    @Override
    public String toString() {
        return restaurant;
    }

    public long getVisitID() {
        return visitID;
    }

    public void setVisitID(long visitID) {
        this.visitID = visitID;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getService() {
        return service;
    }

    public void setService(int service) {
        this.service = service;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }
}
