public class AdminUser extends user {
    public AdminUser(String username, String email, String password) {
        super(username, email, password);
    }
    public String addProduct(product product) {
        return "Admin added product: " + product.getProductname();
    }
    public String removeProduct(product product) {
        return "Admin removed product: " + product.getProductname();
    }
    @Override
    public double applyDiscount(double price) {
        return price * 0.90; 
    }
}