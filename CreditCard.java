public class CreditCard extends PaymentMethod {
    private String cardNumber;

    public CreditCard(String cardNumber) {
        this.cardNumber = cardNumber;
    }
    public String getCardNumber() {
        return cardNumber;
    }
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
    @Override
    public void processPayment(double amount) {
        System.out.println("Paid $" + amount + " using Credit Card: " + cardNumber);
    }
}