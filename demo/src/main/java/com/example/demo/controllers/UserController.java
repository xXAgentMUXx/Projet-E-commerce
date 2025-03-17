package com.example.demo.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Order;
import com.example.demo.models.Role;
import com.example.demo.models.User;
import com.example.demo.services.OrderService;
import com.example.demo.services.UserServices;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserServices userService;

    @Autowired
    private OrderService orderService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody Map<String, String> userData) {
        String username = userData.get("username");
        String email = userData.get("email");
        String password = userData.get("password");
        Role role = Role.valueOf(userData.get("role").toUpperCase());
    
        return ResponseEntity.ok(userService.registerUser(username, email, password, role));
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> loginData) {
    String email = loginData.get("email");
    String password = loginData.get("password");
    Optional<User> authenticatedUser = userService.loginUser(email, password);

    if (authenticatedUser.isPresent()) {
        User user = authenticatedUser.get();
        return ResponseEntity.ok(Map.of("id", user.getId(), "username", user.getUsername()));
    }
    return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
}
    @GetMapping("/{id}/orders")
    public ResponseEntity<List<Order>> getUserOrders(@PathVariable Long id) {
        List<Order> orders = orderService.getUserOrders(id);
        return ResponseEntity.ok(orders);
    }
}