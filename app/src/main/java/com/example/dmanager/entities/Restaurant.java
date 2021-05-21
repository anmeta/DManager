package com.example.dmanager.entities;

public class Restaurant {
    String RestaurantName;
    String RestaurantPhone;
    String RestaurantCity;
    Integer RestaurantStreetNr;
    String RestaurantStreet;

    public Restaurant(){}
    public Restaurant(String RestaurantName,String RestaurantPhone,String RestaurantCity,Integer RestaurantStreetNr, String RestaurantStreet){
        this.RestaurantName= RestaurantName;
        this.RestaurantPhone= RestaurantPhone;
        this.RestaurantCity= RestaurantCity;
        this.RestaurantStreetNr =RestaurantStreetNr;
        this.RestaurantStreet= RestaurantStreet;
    }

    public String getRestaurantName() {
        return RestaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        RestaurantName = restaurantName;
    }

    public String getRestaurantPhone() {
        return RestaurantPhone;
    }

    public void setRestaurantPhone(String restaurantPhone) {
        RestaurantPhone = restaurantPhone;
    }

    public String getRestaurantCity() {
        return RestaurantCity;
    }

    public void setRestaurantCity(String restaurantCity) {
        RestaurantCity = restaurantCity;
    }

    public Integer getRestaurantStreetNr() {
        return RestaurantStreetNr;
    }

    public void setRestaurantStreetNr(Integer restaurantStreetNr) {
        RestaurantStreetNr = restaurantStreetNr;
    }

    public String getRestaurantStreet() {
        return RestaurantStreet;
    }

    public void setRestaurantStreet(String restaurantStreet) {
        RestaurantStreet = restaurantStreet;
    }
}