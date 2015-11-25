package com.example.danielfox.foodchoices;

import android.widget.RatingBar;

/**
 * Created by Daniel Fox on 11/24/2015.
 */
public class Restaurants {
    private String restaurantName;
    private Integer overallService;
    private Long id;
//    private Integer numberVisits;

    public Restaurants() {

    }

    public Restaurants(Long id, String name, Integer service) {
        this.id = id;
        this.restaurantName = name;
        this.overallService = service;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public Integer getOverallService() {
        return overallService;
    }

    public void setOverallService(Integer overallService) {
        this.overallService = overallService;
    }

//    public Integer getNumberVisits() {
//        return numberVisits;
//    }
//
//    public void setNumberVisits(Integer numberVisits) {
//        this.numberVisits = numberVisits;
//    }
}
