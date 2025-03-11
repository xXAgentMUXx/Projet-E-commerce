import java.util.HashMap;
import java.util.Map;

public class panier {
    private String user; 
    private Map<product, Integer> items = new HashMap<>();

    public panier(String user) {
        this.user = user;
    }
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
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