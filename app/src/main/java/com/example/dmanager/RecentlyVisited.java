package com.example.dmanager;


public class RecentlyVisited {
    String orderName;
    String restName;
    Integer imageUrl;


    public RecentlyVisited(String orderName, String restName, Integer imageUrl) {
        this.orderName = orderName;
        this.restName = restName;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return orderName;
    }

    public void setName(String orderName) {
        this.orderName = orderName;
    }

    public String getRestName() {
        return restName;
    }

    public void setRestName(String restName) {
        this.restName = restName;
    }
    public Integer getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(Integer imageUrl) {
        this.imageUrl = imageUrl;
    }
}
