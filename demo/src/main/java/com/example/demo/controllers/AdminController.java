package com.example.demo.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Product;
import com.example.demo.models.Role;
import com.example.demo.models.User;
import com.example.demo.services.ProductService;
import com.example.demo.services.UserServices;

@RestController
@RequestMapping("/admin")
public class AdminController {
    // Injecting ProductService to interact with the database
    @Autowired
    private ProductService productService;

    // Injecting UserService to interact with the database
    @Autowired
    private UserServices userService;

    @PostMapping("/products")
    public ResponseEntity<?> addProduct(@RequestBody Product product, @RequestHeader("userId") Long userId) {
        // Fetch the user by their ID
        Optional<User> optionalUser = userService.findUserById(userId);

        // Check if the user is not found or is not an admin
        if (optionalUser.isEmpty() || optionalUser.get().getRole() != Role.ADMIN) {
            return ResponseEntity.status(403).body("Accès refusé : Seuls les administrateurs peuvent ajouter des produits.");
        }
        // Apply a discount to the product price based on the admin user's
        double priceAfterDiscount = optionalUser.get().applyDiscount(product.getPrice());
        product.setPrice(priceAfterDiscount);

        // Save and return the added product
        Product savedProduct = productService.addProduct(product);
        return ResponseEntity.ok(savedProduct);
    }

    
    @DeleteMapping("/products/{id}")
    public ResponseEntity<?> removeProduct(@PathVariable Long id, @RequestHeader("userId") Long userId) {
        // Fetch the user by their ID to check if the user is an admin
        Optional<User> optionalUser = userService.findUserById(userId);

        // Check if the user is not found or is not an admin
        if (optionalUser.isEmpty() || optionalUser.get().getRole() != Role.ADMIN) {
            return ResponseEntity.status(403).body("Accès refusé : Seuls les administrateurs peuvent supprimer des produits.");
        }
        // Delete the product by its ID
        productService.deleteProduct(id);
        return ResponseEntity.ok("Produit supprimé avec succès !");
    }
}
