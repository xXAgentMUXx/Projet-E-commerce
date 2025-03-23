package com.example.demo.models;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
   // unique identifier for the user
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Username of the user attributes
    private String username;

    // Email of the user attributes
    private String email;

    // Password of the user attributes
    private String password;
    // Orders attributes, user can have multiple orders
    @OneToMany(mappedBy = "user")
    @JsonBackReference
    private List<Order> orderHistory = new ArrayList<>();

    // The role of the user
    @Enumerated(EnumType.STRING)
    private Role role;

    // Default constructor
    public User() {}

      // Constructor to initialize the properties
    public User(String username, String email, String password, Role role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }
    // Getters and setters
    public Long getId() {
        return id;
    }
    public Role getRole() {
        return role;
    }
    public List<Order> getOrders() {
        return orderHistory;
    }
    public void setOrders(List<Order> orderHistory) {
        this.orderHistory = orderHistory;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getEmail() {
        return email;
    }
     // Method to register the user
    public String register() {
        return "User " + username + " registered successfully.";
    }
     // Method to connect the user
    public boolean login(String email, String password) {
        return this.email.equals(email) && this.password.equals(password);
    }
    // Method to view the user's order history
    public List<String> viewOrderHistory() {
        List<String> orders = new ArrayList<>();
        for (Order order : orderHistory) {
            orders.add(order.toString());
        }
        return orders;
    }
     // Method to apply a discount to the price
    public double applyDiscount(double price) {
        return price; 
    }
}