package com.example.demo.models;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {
    // unique identifier for the order
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The order ID for the order
    private String orderID;

    // Each order is associated with a single user
    @ManyToOne
    @JsonBackReference
    private User user;

    // This represents the list of products that are part of the order
    @ElementCollection
    @ManyToMany
    @JoinTable(
    name = "order_items",
    joinColumns = @JoinColumn(name = "order_id"),
    inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> items;

    // Quantities of the products
    @ElementCollection
    private List<Integer> quantities;

    // Status of the order
    private String status;

     // Default constructor
    public Order() {}

    // Constructor to initialize the properties
    public Order(String orderID, User user, List<Product>items, String status, List<Integer> quantities ) {
        this.orderID = orderID;
        this.user = user;
        this.items = items;
        this.status = status;
        this.quantities = quantities;
    }
    // Getter and setters
    public Long getId() {
        return id;
    }
    public List<Integer> getQuantities() {
        return quantities;
    }
    public void setQuantities(List<Integer> quantities) {
        this.quantities = quantities;
    }
    public String getOrderID() {
        return orderID;
    }
    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public List<Product> getItems() {
        return items;
    }
    public void setItems(List<Product> items) {
        this.items = items;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    // Method to update the status
    public String updateStatus(String newStatus) {
        this.status = newStatus;
        return "Order " + orderID + " status updated to: " + status;
    }
    // Method to simulate placing the order
    public String placeOrder() {
        StringBuilder result = new StringBuilder();
        result.append("Order ").append(orderID).append(" placed by ").append(user.getUsername()).append(".\n");
        for (Product itemId : items) {
            result.append("Updating stock for product ID: ").append(itemId).append("...\n");
        }
        return result.toString();
    }
}