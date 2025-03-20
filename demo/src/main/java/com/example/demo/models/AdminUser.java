package com.example.demo.models;

import jakarta.persistence.Entity;

@Entity
public class AdminUser extends User {

    // Default constructor that calls the superclass
    public AdminUser() {
        super(); 
    }
     // Constructor that initializes the properties
    public AdminUser(String username, String email, String password) {
        super(username, email, password, Role.ADMIN);
    }
      // Method for adding a product by the admin.
    public String addProduct(Product product) {
        return "Admin added product: " + product.getProductname();
    }
    // Method for removing a product by the admin.
    public String removeProduct(Product product) {
        return "Admin removed product: " + product.getProductname();
    }
    // Override the applyDiscount method that reduce the price
    @Override
    public double applyDiscount(double price) {
        return price * 0.90; 
    }
}