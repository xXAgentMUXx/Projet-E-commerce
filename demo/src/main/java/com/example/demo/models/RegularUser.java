package com.example.demo.models;

import jakarta.persistence.Entity;

@Entity
public class RegularUser extends User {

    // Default Constructor
    public RegularUser() {
        super(); 
    }
    //Constructor to initialize the properties
    public RegularUser(String username, String email, String password) {
        super(username, email, password, Role.REGULAR);
    }
    // Method to reduce price for users
    @Override
    public double applyDiscount(double price) {
        return price * 0.95; 
    }
}