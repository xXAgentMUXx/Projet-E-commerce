package com.example.demo.models;

import java.util.HashMap;
import java.util.Map;
import jakarta.persistence.*;

@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private user user; 
    
    @ElementCollection
    @CollectionTable(name = "cart_items", joinColumns = @JoinColumn(name = "cart_id"))
    @MapKeyJoinColumn(name = "product_id")
    @Column(name = "quantity")
    private Map<product, Integer> items = new HashMap<>();

    public Cart() {}

    public Cart(user user) {
        this.user = user;
    }
    public Long getId() {
        return id;
    }
    public user getUser() {
        return user;
    }
    public void setUser(user user) {
        this.user = user;
    }
    public void removeProduct(product product) {
        items.remove(product);
    }
    public void addProduct(product product, int quantity) {
        items.put(product, items.getOrDefault(product, 0) + quantity);
    }
    public double calculateTotal() {
        double total = 0;
        for (Map.Entry<product, Integer> entry : items.entrySet()) {
            total += entry.getKey().getPrice() * entry.getValue();
        }
        return total;
    }
}