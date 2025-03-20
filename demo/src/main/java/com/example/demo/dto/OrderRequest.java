package com.example.demo.dto;

import java.util.List;

public class OrderRequest {
    private Long userId;
    private List<Long> productIds;
    private List<Integer> quantities;

    public OrderRequest() {}

    public OrderRequest(Long userId, List<Long> productIds, List<Integer> quantities) {
        this.userId = userId;
        this.productIds = productIds;
        this.quantities = quantities;
    }
    public Long getUserId() { 
        return userId; 
    }
    public void setUserId(Long userId) { 
        this.userId = userId; 
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