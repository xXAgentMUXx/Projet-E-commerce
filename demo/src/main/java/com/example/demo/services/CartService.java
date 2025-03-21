package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Cart;
import com.example.demo.models.Product;
import com.example.demo.models.User;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;

@Service
public class CartService {
    // Injecting CartRepository to interact with the database
    @Autowired
    private CartRepository cartRepository;
    // Injecting UserRepository to interact with the database
    @Autowired
    private UserRepository userRepository;
    // Injecting ProductRepository to interact with the database
    @Autowired
    private ProductRepository productRepository;

    public Cart getCartByUser(Long userId) {
        // Fetching the user by ID
        User user = userRepository.findById(userId).orElseThrow();

        // Fetching the cart associated with the user
        return cartRepository.findByUser(user).orElseGet(() -> {

            // Creating a new cart if none exists
            Cart newCart = new Cart(user);
            return cartRepository.save(newCart);
        });
    }
    public Cart addProductToCart(Long userId, Long productId, int quantity) {
        // Retrieving the user's cart
        Cart cart = getCartByUser(userId);
        Product product = productRepository.findById(productId).orElseThrow();

        // Adding the product to the cart
        cart.addProduct(product, quantity);

        // Saving and returning the updated cart
        return cartRepository.save(cart);
    }
    public Cart removeProductFromCart(Long userId, Long productId) {
        // Retrieving the user's cart
        Cart cart = getCartByUser(userId);
        Product product = productRepository.findById(productId).orElseThrow();
        cart.removeProduct(product);

         // Saving and returning the updated cart
        return cartRepository.save(cart);
    }
    // Retrieving the user's cart and calculating the total cost
    public double getCartTotal(Long userId) {
        return getCartByUser(userId).calculateTotal();
    }
}