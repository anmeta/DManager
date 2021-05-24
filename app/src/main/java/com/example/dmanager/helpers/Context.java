package com.example.dmanager.helpers;

import com.example.dmanager.entities.Restaurant;
import com.example.dmanager.entities.User;

import java.util.ArrayList;

/*
This class will be used to save/share data among activities
It is created using a singleton pattern
Ensure a class has only one instance, and provide a global point of access to it.
*/

public class Context {
    public User activeUser;
    public Restaurant activeRestaurant;
    public ArrayList<Restaurant> restaurantsForDisplay = new ArrayList<Restaurant>();
    public ArrayList<Restaurant> lastViewedRestaurants = new ArrayList<Restaurant>();

    private static final Context singletonContext = new Context();
    public static Context getInstance() {return singletonContext;}

    public void addRestaurant(Restaurant restaurant) {
        restaurantsForDisplay.add(restaurant);
    }
    public void addViewedRestaurant(Restaurant restaurant){
        if(lastViewedRestaurants.contains(restaurant)){
            lastViewedRestaurants.remove(restaurant);
        }
        //add on the beginning
        lastViewedRestaurants.add(0, restaurant);
    }
    public void cleanContext(){
        activeUser = null;
        activeRestaurant = null;
        restaurantsForDisplay = new ArrayList<Restaurant>();
        lastViewedRestaurants = new ArrayList<Restaurant>();
    }
}
