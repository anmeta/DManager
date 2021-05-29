package com.example.dmanager.entities;

public class User {
    public String PacientName;
    public String PacientSurname;
    public String PacientNumber;
    public String LivingCity;
    public String Email;
    public String Password;
    public Integer Age;

    public User(String PacientName, String PacientSurname,String PacientNumber,
                String LivingCity, Integer Age, String Email, String Password) {
        this.PacientName = PacientName;
        this.PacientSurname = PacientSurname;
        this.PacientNumber= PacientNumber;
        this.LivingCity= LivingCity;
        this.Age=Age;
        this.Email = Email;
        this.Password = Password;
    }

    public String getFullName(){
        return this.PacientName + " " + this.PacientSurname;
    }
}
