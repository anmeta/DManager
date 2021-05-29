package com.example.dmanager.entities;

public class Restaurant {
    public String RestaurantName;
    public String RestaurantPhone;
    public String RestaurantCity;
    public Integer RestaurantStreetNr;
    public String RestaurantStreet;
    public String Email;
    public String Password;

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
}