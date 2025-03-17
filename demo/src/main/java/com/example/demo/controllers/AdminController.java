package com.example.demo.controllers;

import com.example.demo.models.Product;
import com.example.demo.models.User;
import com.example.demo.models.Role;
import com.example.demo.services.ProductService;
import com.example.demo.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private ProductService productService;
    @Autowired
    private UserServices userService;

    // Vérification du rôle avant d'ajouter un produit
    @PostMapping("/products")
    public ResponseEntity<?> addProduct(@RequestBody Product product, @RequestHeader("userId") Long userId) {
        Optional<User> optionalUser = userService.findUserById(userId);

        if (optionalUser.isEmpty() || optionalUser.get().getRole() != Role.ADMIN) {
            return ResponseEntity.status(403).body("⛔️ Accès refusé : Seuls les administrateurs peuvent ajouter des produits.");
        }

        // Appliquer la réduction en fonction du rôle
        double priceAfterDiscount = optionalUser.get().applyDiscount(product.getPrice());
        product.setPrice(priceAfterDiscount);

        Product savedProduct = productService.addProduct(product);
        return ResponseEntity.ok(savedProduct);
    }

    // Vérification du rôle avant de supprimer un produit
    @DeleteMapping("/products/{id}")
    public ResponseEntity<?> removeProduct(@PathVariable Long id, @RequestHeader("userId") Long userId) {
        Optional<User> optionalUser = userService.findUserById(userId);

        if (optionalUser.isEmpty() || optionalUser.get().getRole() != Role.ADMIN) {
            return ResponseEntity.status(403).body("⛔️ Accès refusé : Seuls les administrateurs peuvent supprimer des produits.");
        }

        productService.deleteProduct(id);
        return ResponseEntity.ok("✅ Produit supprimé avec succès !");
    }
}
