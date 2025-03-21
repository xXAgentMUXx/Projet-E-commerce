package com.example.demo.dto;

import java.util.List;

public class OrderRequest {
    // ID of the user attributes
    private Long userId;
    // List of product IDs attributes
    private List<Long> productIds;
    // List of quantities attributes
    private List<Integer> quantities;

    // Default constructors
    public OrderRequest() {}

    // Constructor to initialize properties
    public OrderRequest(Long userId, List<Long> productIds, List<Integer> quantities) {
        this.userId = userId;
        this.productIds = productIds;
        this.quantities = quantities;
    }
    // Getters and setters
    public Long getUserId() { 
        return userId; 
    }
    public List<Long> getProductIds() { 
        return productIds; 
    }
    public void setProductIds(List<Long> productIds) { 
        this.productIds = productIds; 
    }
    public List<Integer> getQuantities() { 
        return quantities; 
    }
    public void setQuantities(List<Integer> quantities) { 
        this.quantities = quantities; 
    }
}