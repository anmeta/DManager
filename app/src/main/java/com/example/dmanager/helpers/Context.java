package com.example.dmanager.helpers;
import com.example.dmanager.entities.MenuItem;
import com.example.dmanager.entities.Restaurant;
import com.example.dmanager.entities.User;
import java.util.ArrayList;

/*
@author: Anna Maria Meta
@description:
This class is created using a singleton pattern.
The purpose is to save the information needed among activities (similar to Session in the web)
Is ensures a class has only one instance, and provide a global point of access to it.
*/

public class Context {
    public User activeUser;
    public Restaurant activeRestaurant;
    public MenuItem activeMenuItem;
    public String LastSignedInEmail;
    public ArrayList<Restaurant> restaurantsForDisplay = new ArrayList<Restaurant>();
    public ArrayList<Restaurant> lastViewedRestaurants = new ArrayList<Restaurant>();
    public ArrayList<String> userRestaurantEmails = new ArrayList<String>();

    private static final Context singletonContext = new Context();
    public static Context getInstance() {return singletonContext;}

    public void addRestaurant(Restaurant restaurant) {
        restaurantsForDisplay.add(restaurant);
    }
    public void addViewedRestaurant(Restaurant restaurant){
        if(lastViewedRestaurants.contains(restaurant)){
            lastViewedRestaurants.remove(restaurant);
        }
        //add on the beginning of the list
        lastViewedRestaurants.add(0, restaurant);
    }
    public void cleanContext(){
        //Save email
        if(activeUser!=null)LastSignedInEmail = activeUser.Email;
        if(activeRestaurant!=null)LastSignedInEmail = activeRestaurant.Email;

        //Flush
        activeUser = null;
        activeRestaurant = null;
        restaurantsForDisplay = new ArrayList<Restaurant>();
        lastViewedRestaurants = new ArrayList<Restaurant>();
    }
}
