package com.example.dmanager.entities;

public class User {
    public String PacientName;
    public String PacientSurname;
    public String PacientNumber;
    public String LivingCity;
    public Integer Age;
    public User(){

   }

    public User(String PacientName, String PacientSurname,String PacientNumber, String LivingCity, Integer Age) {
        this.PacientName = PacientName;
        this.PacientSurname = PacientSurname;
        this.PacientNumber= PacientNumber;
        this.LivingCity= LivingCity;
        this.Age=Age;
    }

    public String getPacientName() {
        return PacientName;
    }

    public void setPacientName(String pacientName) {
        PacientName = pacientName;
    }

    public String getPacientSurname() {
        return PacientSurname;
    }

    public void setPacientSurname(String pacientSurname) {
        PacientSurname = pacientSurname;
    }

    public String getPacientNumber() {
        return PacientNumber;
    }

    public void setPacientNumber(String PacientNumber) {
        PacientNumber = PacientNumber;
    }



    public String getCity() {
        return LivingCity;
    }

    public void setCity(String city) {
        LivingCity = city;
    }

    public Integer getAge() {
        return Age;
    }

    public void setAge(Integer age) {
        Age = age;
    }
    public String getFullName(){
        return this.PacientName + " " + this.getPacientSurname();
    }
}
