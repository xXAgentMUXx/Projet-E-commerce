package com.example.demo.models;

public class RegularUser extends user {
    public RegularUser(String username, String email, String password) {
        super(username, email, password);
    }
    @Override
    public double applyDiscount(double price) {
        return price * 0.95; 
    }
}