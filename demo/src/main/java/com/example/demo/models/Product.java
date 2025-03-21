package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Product {
    //  unique identifier for the product
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Name of the product attributes
    @JsonProperty("name")

    // Unique product ID attributes
    private String productname;


    private int productID;

    // Price of the product attributes
    private double price;

    //  Quantity of the product attributes
    @JsonProperty("stock")
    private int Stockquantity;

     // Default constructor
    public Product() {}

    // Constructor to initialize the properties
    public Product(String productname, int productID, double price, int Stockquantity ) {
        this.productname = productname;
        this.productID = productID;
        this.price = price;
        this.Stockquantity = Stockquantity;
    }
    // Getter and setters
    public Long getId() {
        return id;
    }
    public String getProductname() {
        return productname;
    }
    public void setProductname(String productname) {
        this.productname = productname;
    }
    public int getProductID() {
        return productID;
    }
    public void setProductID(int productID) {
        this.productID = productID;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public int getStockquantity() {
        return Stockquantity;
    }
    public void setStockquantity(int Stockquantity) {
        this.Stockquantity = Stockquantity;
    }
     // Method to update the stock of the product
    public String updateStock(int quantity) {
        if (quantity > 0 && Stockquantity >= quantity) {
            this.Stockquantity -= quantity;
            return "Stock updated successfully. New stock quantity: " + Stockquantity;
        } else {
            return "Stock insufficient or invalid quantity.";
        }
    }
      // Method to get the product details
    public String getProductDetails() {
        return "Product ID: " + productID + ", Name: " + productname + ", Price: $" + price + ", Stock: " + Stockquantity;
    }
}