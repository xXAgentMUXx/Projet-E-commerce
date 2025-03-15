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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderID;
    @ManyToOne
    @JsonBackReference
    private User user;
    @ElementCollection
    @ManyToMany
    @JoinTable(
    name = "order_items",
    joinColumns = @JoinColumn(name = "order_id"),
    inverseJoinColumns = @JoinColumn(name = "product_id")
)
    private List<Product> items;
    private String status;

    public Order() {}

    public Order(String orderID, User user, List<Product>items, String status ) {
        this.orderID = orderID;
        this.user = user;
        this.items = items;
        this.status = status;
    }
    public Long getId() {
        return id;
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
    public String updateStatus(String newStatus) {
        this.status = newStatus;
        return "Order " + orderID + " status updated to: " + status;
    }
    public String placeOrder() {
        StringBuilder result = new StringBuilder();
        result.append("Order ").append(orderID).append(" placed by ").append(user.getUsername()).append(".\n");
        for (Product itemId : items) {
            result.append("Updating stock for product ID: ").append(itemId).append("...\n");
        }
        return result.toString();
    }
}