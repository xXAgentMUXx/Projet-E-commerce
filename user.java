
import java.util.ArrayList;
import java.util.List;

public class user {
    private String username;
    private String email;
    private String password;
    private List<Double> orders = new ArrayList<>();

    public user(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
    public List<Double> getOrders() {
        return orders;
    }
    public void setOrders(List<Double> orders) {
        this.orders = orders;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

}