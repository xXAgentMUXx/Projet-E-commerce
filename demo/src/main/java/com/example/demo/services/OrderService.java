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
    // Injecting orderRepository to interact with the database
    @Autowired
    private orderRepository orderRepository;
    // Injecting ProductRepository to interact with the database
    @Autowired
    private ProductRepository productRepository;
    // Injecting UserRepository to interact with the database
    @Autowired
    private UserRepository userRepository;


    public Order placeOrder(Long userId, List<Long> productIds, List<Integer> quantities) {
         // Check for invalid userId or productIds
        if (userId == null || productIds == null || productIds.isEmpty()) {
            throw new RuntimeException("❌ userId ou productIds est NULL !");
        }
        // Fetch the user by ID, return an exception if not found
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("❌ Utilisateur introuvable avec ID: " + userId));
        
        // Retrieve the products by its ID
        List<Product> products = productRepository.findAllById(productIds);
        if (products.isEmpty()) {
            throw new RuntimeException("❌ Aucun produit valide trouvé. IDs envoyés: " + productIds);
        }
        // Check if no valid products were found
        if (products.size() != productIds.size()) {
            throw new RuntimeException("❌ Certains produits envoyés n'existent pas en base.");
        }
        // Ensure that all product IDs match
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            int quantityOrdered = quantities.get(i);
            
            if (quantityOrdered > product.getStockquantity()) {
                throw new RuntimeException("❌ Quantité demandée pour " + product.getProductname() + " dépasse le stock disponible. Stock actuel: " + product.getStockquantity());
            }
        }
        String orderID = UUID.randomUUID().toString();
        Order order = new Order(orderID, user, products, "Processing", quantities);
        orderRepository.save(order);
        
        // Check if the ordered quantities do not exceed the available stock
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            int quantityOrdered = quantities.get(i);
            
             // Update product stock and save
            product.updateStock(quantityOrdered); 
            
            // If stock goes negative, delete the product from order
            if (product.getStockquantity() < 0) {
                productRepository.delete(product); 
            } else {
                productRepository.save(product); 
            }
        }
        return order;
    }
    // Retrieve and return the orders placed by the user
    public List<Order> getUserOrders(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return orderRepository.findByUser(user);
    }
    //Retrieves an order by its order ID
    public Optional<Order> getOrderById(Long orderId) {
        return orderRepository.findById(orderId);
    }
}