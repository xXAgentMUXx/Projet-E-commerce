public class PayPal extends PaymentMethod {
    private String email;

    public PayPal(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    @Override
    public void processPayment(double amount) {
        System.out.println("Paid $" + amount + " using PayPal: " + email);
    }
}