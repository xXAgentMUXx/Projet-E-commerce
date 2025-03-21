package com.example.demo.models;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.dto.CartItem;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Cart {
    // identifier for the cart
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Each cart is associated with a specific user with User entity
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user; 
    
     // A cart can contain multiple CartItems
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "cart_id")
    private List<CartItem> items = new ArrayList<>();

     // Default constructor for the JPA
    public Cart() {}

    // Constructor to initialize properties
    public Cart(User user) {
        this.user = user;
    }
    // Getter and setters for each attributs
    public List<CartItem> getItems() {
        return items;
    }
    public void setItems(List<CartItem> items) {
        this.items = items;
    }
    public Long getId() {
        return id;
    }
    public User getUser() {
        return user;
    }
    // Method to remove a product from the cart
    public void removeProduct(Product product) {
        items.removeIf(item -> item.getProduct().equals(product));
    }
     // Method to add a product to the cart with a specified quantity
    public void addProduct(Product product, int quantity) {
        for (CartItem item : items) {
            if (item.getProduct().equals(product)) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        items.add(new CartItem(product, quantity));
    }
     // Method to calculate the total price of all items in the cart
    public double calculateTotal() {
        return items.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
    }
    // Method to clear all items from the cart
    public void clear() {
        items.clear();
    }
}