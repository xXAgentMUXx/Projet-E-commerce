package com.example.demo.models;

import jakarta.persistence.Entity;

@Entity
public class RegularUser extends User {
    public RegularUser() {
        super(); 
    }
    public RegularUser(String username, String email, String password) {
        super(username, email, password, Role.REGULAR);
    }
    @Override
    public double applyDiscount(double price) {
        return price * 0.95; 
    }
}