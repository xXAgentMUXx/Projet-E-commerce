package com.example.demo.repository;

import com.example.demo.models.Cart;
import com.example.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// CartRepository interface extends JpaRepository to provide CRUD operations for the Cart entity
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(User user);
}