package com.example.dmanager;

public class PopularRest {
    String name;
    String location;
    Integer rating;

    public PopularRest(String name, String location, Integer rating) {
        this.name = name;
        this.location = location;
        this.rating = rating;


    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer imageUrl) {
        this.rating = rating;
    }



}
