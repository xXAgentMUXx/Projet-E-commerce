package com.example.demo.controllers;

import com.example.demo.models.Cart;
import com.example.demo.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping("/{userId}")
    public Cart getCart(@PathVariable Long userId) {
        return cartService.getCartByUser(userId);
    }
    @PostMapping("/add")
    public Cart addProduct(@RequestParam Long userId, @RequestParam Long productId, @RequestParam int quantity) {
        return cartService.addProductToCart(userId, productId, quantity);
    }
    @PostMapping("/remove")
    public Cart removeProduct(@RequestParam Long userId, @RequestParam Long productId) {
        return cartService.removeProductFromCart(userId, productId);
    }
    @GetMapping("/total/{userId}")
    public double getCartTotal(@PathVariable Long userId) {
        return cartService.getCartTotal(userId);
    }
}