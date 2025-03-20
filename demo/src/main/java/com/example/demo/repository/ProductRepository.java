package com.example.demo.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.Product;

// ProductRepository interface extends JpaRepository to provide operations for the Product entity
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}