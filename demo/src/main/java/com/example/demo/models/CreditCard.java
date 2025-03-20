package com.example.demo.models;

public class CreditCard extends PaymentMethod {
    // Credit card number attributes
    private String cardNumber;

      // Constructor to initialize the properties
    public CreditCard(String cardNumber) {
        this.cardNumber = cardNumber;
    }
     // Getter and setters for the card number
    public String getCardNumber() {
        return cardNumber;
    }
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
     // Override the processPayment method to print payment details
    @Override
    public void processPayment(double amount) {
        System.out.println("Paid $" + amount + " using Credit Card: " + cardNumber);
    }
}