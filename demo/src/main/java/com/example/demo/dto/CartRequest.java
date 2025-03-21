package com.example.demo.dto;

public class CartRequest {
    // ID of the user attributes
    private Long userId;
    // ID of the product attributes
    private Long productId;
    // Quantity of the product attributes
    private int quantity;

    // Default constructors
    public CartRequest() {}

    // Constructor to initialize properties
    public CartRequest(Long userId, Long productId, int quantity) {
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
    }
    // Getters and setters
    public Long getUserId() { 
        return userId; 
    }
    public Long getProductId() { 
        return productId; 
    }
    public int getQuantity() { 
        return quantity; 
    }
}