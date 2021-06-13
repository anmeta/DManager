package com.example.dmanager.entities;

import com.google.firebase.firestore.UserDataReader;

public class UserRestaurant {
    public String UserEmail;
    public String RestaurantEmail;
    public boolean IsFavorite;
    public UserRestaurant(String userEmail, String restaurantEmail, boolean isFavorite){
        UserEmail = userEmail;
        RestaurantEmail = restaurantEmail;
        IsFavorite = isFavorite;
    }
    public UserRestaurant(){}
}
