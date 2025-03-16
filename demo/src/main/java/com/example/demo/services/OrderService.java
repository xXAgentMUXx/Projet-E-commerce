package com.example.demo.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Cart;
import com.example.demo.models.Order;
import com.example.demo.models.Product;
import com.example.demo.models.User;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.orderRepository;

@Service
public class OrderService {
    @Autowired
    private orderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartService cartService;
    @Autowired
    private CartRepository cartRepository;

    public Order placeOrder(Long userId, List<Long> productIds) {
    try {
        System.out.println("🔹 Tentative de commande pour userId: " + userId);
        System.out.println("🔹 Produits commandés: " + productIds);

        // Vérifier si l'utilisateur existe
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("❌ Utilisateur introuvable avec ID: " + userId));

        // Vérifier si les produits existent
        List<Product> products = productRepository.findAllById(productIds);
        if (products.isEmpty()) {
            throw new RuntimeException("❌ Aucun produit valide trouvé en base de données. IDs envoyés: " + productIds);
        }

        // Vérifier si tous les produits demandés existent
        if (products.size() != productIds.size()) {
            throw new RuntimeException("❌ Certains produits envoyés n'existent pas en base.");
        }

        // Création de la commande
        String orderID = UUID.randomUUID().toString();
        Order order = new Order(orderID, user, products, "Processing");
        orderRepository.save(order);

        // Vérifier le panier avant de le vider
        Cart cart = cartService.getCartByUser(userId);
        if (cart.getItems().isEmpty()) {
            System.out.println("⚠️ Attention : Le panier était déjà vide !");
        }

        // Vider le panier après la commande
        cart.clear();
        cartRepository.save(cart);

        System.out.println("✅ Commande réussie pour l'utilisateur " + userId);
        return order;

    } catch (Exception e) {
        System.err.println("🔥 ERREUR lors du passage de commande : " + e.getMessage());
        e.printStackTrace();
        throw new RuntimeException("Échec de la commande : " + e.getMessage());
    }
}

    public List<Order> getUserOrders(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return orderRepository.findByUser(user);
    }
    public Optional<Order> getOrderById(Long orderId) {
        return orderRepository.findById(orderId);
    }
}