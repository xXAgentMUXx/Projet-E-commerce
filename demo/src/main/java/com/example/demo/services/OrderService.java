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
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Cart cart = cartService.getCartByUser(userId);
    
        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }
    
        // Filtrer uniquement les produits commandés
        List<Product> products = productRepository.findAllById(productIds);
        
        if (products.isEmpty()) {
            throw new RuntimeException("Aucun produit valide trouvé pour la commande.");
        }
    
        String orderID = UUID.randomUUID().toString();
        Order order = new Order(orderID, user, products, "Processing");
        orderRepository.save(order);
    
        cart.clear();
        cartRepository.save(cart);
    
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
