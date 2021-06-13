package com.example.dmanager.entities;

public class MenuItem {
    public String ItemDescription;
    public String ItemIngredients;
    public String ItemName;
    public String ItemPrice;
    public MenuItem(String name, String description, String ingredients, String price){
        this.ItemName = name;
        this.ItemDescription = description;
        this.ItemIngredients = ingredients;
        this.ItemPrice = price;
    }
    public MenuItem(){}
}
