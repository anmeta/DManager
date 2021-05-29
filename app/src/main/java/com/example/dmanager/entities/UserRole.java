package com.example.dmanager.entities;
import com.example.dmanager.helpers.Roles;

public class UserRole {
    public String Email;
    public Roles Role;

    public UserRole(String Email, Roles Role) {
        this.Email = Email;
        this.Role = Role;
    }

}
