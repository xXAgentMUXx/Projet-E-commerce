package com.example.demo.services;

import com.example.demo.models.Cart;
import com.example.demo.models.Product;
import com.example.demo.models.User;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;
    public Cart getCartByUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return cartRepository.findByUser(user).orElseGet(() -> {
            Cart newCart = new Cart(user);
            return cartRepository.save(newCart);
        });
    }
    public Cart addProductToCart(Long userId, Long productId, int quantity) {
        Cart cart = getCartByUser(userId);
        Product product = productRepository.findById(productId).orElseThrow();
        cart.addProduct(product, quantity);
        return cartRepository.save(cart);
    }
    public Cart removeProductFromCart(Long userId, Long productId) {
        Cart cart = getCartByUser(userId);
        Product product = productRepository.findById(productId).orElseThrow();
        cart.removeProduct(product);
        return cartRepository.save(cart);
    }
    public double getCartTotal(Long userId) {
        return getCartByUser(userId).calculateTotal();
    }
}