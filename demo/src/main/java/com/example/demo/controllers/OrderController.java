package com.example.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.OrderRequest;
import com.example.demo.models.Order;
import com.example.demo.services.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {
    // Injecting OrderService to interact with the database
    @Autowired
    private OrderService orderService;

    //Places a new order for a user
    @PostMapping("/place")
    public ResponseEntity<Order> placeOrder(@RequestBody OrderRequest request) {
    // Validate the request: userId and productIds must not be null or empty
    if (request.getUserId() == null || request.getProductIds() == null || request.getProductIds().isEmpty()) {
        return ResponseEntity.badRequest().build();
    }
    return ResponseEntity.ok(orderService.placeOrder(request.getUserId(), request.getProductIds(), request.getQuantities()));
}
    // Retrieves an order by its ID
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        // Retrieve the order by ID and return it, or return a not found response if it doesn't exist
        return orderService.getOrderById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}