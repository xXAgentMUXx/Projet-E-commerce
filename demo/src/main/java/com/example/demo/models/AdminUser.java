package com.example.demo.models;

import jakarta.persistence.Entity;

@Entity
public class AdminUser extends User {

    public AdminUser() {
        super(); 
    }

    public AdminUser(String username, String email, String password) {
        super(username, email, password, Role.ADMIN);
    }
    public String addProduct(Product product) {
        return "Admin added product: " + product.getProductname();
    }
    public String removeProduct(Product product) {
        return "Admin removed product: " + product.getProductname();
    }
    @Override
    public double applyDiscount(double price) {
        return price * 0.90; 
    }
}