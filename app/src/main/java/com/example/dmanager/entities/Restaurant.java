package com.example.dmanager.entities;

import java.util.ArrayList;

public class Restaurant {
    public String RestaurantName;
    public String RestaurantPhone;
    public String RestaurantCity;
    public Integer RestaurantStreetNr;
    public String RestaurantStreet;
    public String Email;
    public String Password;
    public ArrayList<MenuItem> MenuItems = new ArrayList<MenuItem>();

    public Restaurant(String RestaurantName,String RestaurantPhone,String RestaurantCity,
                      Integer RestaurantStreetNr, String RestaurantStreet, String Email, String Password){
        this.RestaurantName= RestaurantName;
        this.RestaurantPhone= RestaurantPhone;
        this.RestaurantCity= RestaurantCity;
        this.RestaurantStreetNr =RestaurantStreetNr;
        this.RestaurantStreet= RestaurantStreet;
        this.Email = Email;
        this.Password = Password;
    }
    public Restaurant(String RestaurantName,String RestaurantPhone,String RestaurantCity,
                      Integer RestaurantStreetNr, String RestaurantStreet){
        this.RestaurantName= RestaurantName;
        this.RestaurantPhone= RestaurantPhone;
        this.RestaurantCity= RestaurantCity;
        this.RestaurantStreetNr =RestaurantStreetNr;
        this.RestaurantStreet= RestaurantStreet;
    }
    public String getFullName(){
        return this.RestaurantName + " " ;
    }

    public ArrayList<String> getDetails() {
        ArrayList<String> details = new ArrayList<String>();
        details.add("NAME      " + this.RestaurantName);
        details.add("PHONE     " + this.RestaurantPhone);
        details.add("ADDRESS   " + this.RestaurantStreet + " " + this.RestaurantStreetNr);
        details.add("CITY      " + this.RestaurantCity);

        return details;
    }
    public ArrayList<String> getMenuItems() {
        ArrayList<String> items = new ArrayList<String>();
        for (MenuItem item:this.MenuItems) {
            items.add(item.ItemName + " - " + item.ItemPrice);
        }
        return items;
    }
}