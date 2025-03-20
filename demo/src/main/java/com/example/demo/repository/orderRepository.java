package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Order;
import com.example.demo.models.User;

// orderRepository interface extends JpaRepository to provide CRUD operations for the Order entity
@Repository
public interface orderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
}