package com.example.demo.dto;

import com.example.demo.models.Product;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;


@Entity
public class CartItem {
    // Unique identifier for the cart item
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The product associated with the cart item attributes
    @ManyToOne
    private Product product;

    // Quantity of the product in the cart item attributes
    private int quantity;

    // Default constructors
    public CartItem() {}

    // Constructor to initialize properties
    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }
    // Getters and setters
    public Long getId() {
        return id;
    }
    public Product getProduct() {
        return product;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}