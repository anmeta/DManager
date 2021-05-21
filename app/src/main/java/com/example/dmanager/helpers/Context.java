package com.example.dmanager.helpers;

import com.example.dmanager.entities.Restaurant;
import com.example.dmanager.entities.User;

/*
This class will be used to save/share data among activities
It is created using a singleton pattern
Ensure a class has only one instance, and provide a global point of access to it.
*/

public class Context {
    public User activeUser;
    public Restaurant activeRestaurant;

    private static final Context singletonContext = new Context();
    public static Context getInstance() {return singletonContext;}
}
