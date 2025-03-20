package com.example.demo.models;

public class PayPal extends PaymentMethod {
    // Attributes for the email
    private String email;

    //Constructor to initialize the properties
    public PayPal(String email) {
        this.email = email;
    }
    // Getters and Setters
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    // Method override to process the payment by the user
    @Override
    public void processPayment(double amount) {
        System.out.println("Paid $" + amount + " using PayPal: " + email);
    }
}