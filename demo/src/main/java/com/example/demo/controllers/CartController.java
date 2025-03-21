package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.CartRequest;
import com.example.demo.models.Cart;
import com.example.demo.services.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {
    // Injecting CartService to interact with the database
    @Autowired
    private CartService cartService;

    // Fetch and return the cart associated with the user
    @GetMapping("/{userId}")
    public Cart getCart(@PathVariable Long userId) {
        return cartService.getCartByUser(userId);
    }
    // Add the product to the cart and return the updated cart
    @PostMapping("/add")
    public Cart addProduct(@RequestBody CartRequest request) {
    return cartService.addProductToCart(request.getUserId(), request.getProductId(), request.getQuantity());
    }
    // Delete the product to the cart and return the updated cart
    @PostMapping("/remove")
    public Cart removeProduct(@RequestBody CartRequest request) {
        return cartService.removeProductFromCart(request.getUserId(), request.getProductId());
    }
    // Calculate and return the total price of the cart
    @GetMapping("/total/{userId}")
    public double getCartTotal(@PathVariable Long userId) {
        return cartService.getCartTotal(userId);
    }
}