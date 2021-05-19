package com.example.dmanager;

public class User {
    String PacientName;
    String PacientSurname;
    Integer PacientNumber;
    String LivingCity;
    Integer Age;
    public User(){

   }

    public User(String PacientName, String PacientSurname,Integer PacientNumber, String City, Integer Age) {
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

    public Integer getPacientNumber() {
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
}
