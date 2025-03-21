package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Product;
import com.example.demo.repository.ProductRepository;


@Service
public class ProductService {
    // Injecting ProductRepository to interact with the database
    @Autowired
    private ProductRepository productRepository;

    // Save and create the new product
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }
    // Retrieves all products from the database
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    // Retrieve and return the product by its ID
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }
    // Deletes a product by its ID
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}