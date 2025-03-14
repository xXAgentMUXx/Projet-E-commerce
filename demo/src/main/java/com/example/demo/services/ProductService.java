package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.product;
import com.example.demo.repository.ProductRepository;


@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public product addProduct(product product) {
        return productRepository.save(product);
    }

    public List<product> getAllProducts() {
        return productRepository.findAll();
    }
    public Optional<product> getProductById(Long id) {
        return productRepository.findById(id);
    }
}