package com.example.demo.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Order;
import com.example.demo.models.Product;
import com.example.demo.models.User;
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


    public Order placeOrder(Long userId, List<Long> productIds) {

        if (userId == null || productIds == null || productIds.isEmpty()) {
            throw new RuntimeException("❌ userId ou productIds est NULL !");
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("❌ Utilisateur introuvable avec ID: " + userId));
    
        List<Product> products = productRepository.findAllById(productIds);
        if (products.isEmpty()) {
            throw new RuntimeException("❌ Aucun produit valide trouvé. IDs envoyés: " + productIds);
        }
        if (products.size() != productIds.size()) {
            throw new RuntimeException("❌ Certains produits envoyés n'existent pas en base.");
        }
        String orderID = UUID.randomUUID().toString();
        Order order = new Order(orderID, user, products, "Processing");
        orderRepository.save(order);
    
        return order;
    }
    public List<Order> getUserOrders(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return orderRepository.findByUser(user);
    }
    public Optional<Order> getOrderById(Long orderId) {
        return orderRepository.findById(orderId);
    }
}