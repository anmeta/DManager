package com.example.dmanager;

public class Restaurants {
    String RestaurantName;
    Integer RestaurantPhone;
    String RestaurantCity;
    Integer RestaurantStreetNr;
    String RestaurantStreet;

    public Restaurants(){}
    public Restaurants(String RestaurantName,Integer RestaurantPhone,String RestaurantCity,Integer RestaurantStreetNr, String RestaurantStreet){
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

    public Integer getRestaurantPhone() {
        return RestaurantPhone;
    }

    public void setRestaurantPhone(Integer restaurantPhone) {
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
