package com.example.danielfox.foodchoices;


public class User {

    private long userID;
    private String username;
    private String password;

    public User() {

    }

    public User(long id, String username, String password) {
        this.userID = id;
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return username;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
