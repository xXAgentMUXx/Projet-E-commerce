import java.util.ArrayList;
import java.util.List;

public class User {
    private String userName;
    private String email;
    private String password;
    private List<String> orderHistory = new ArrayList<>();

    public User(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUsername(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getOrderHistory() {
        return orderHistory;
    }

    public void setOrderHistory(List<String> orderHistory) {
        this.orderHistory = orderHistory;
    }


    public void register() {
        System.out.println("User " + userName + " registered successfully!");
    }

    public boolean login(String enteredPassword) {
        if (this.password.equals(enteredPassword)) {
            System.out.println("Login successful for " + userName);
            return true;
        } else {
            System.out.println("Invalid credentials for " + userName);
            return false;
        }
    }

    public void viewOrderHistory() {
        if (orderHistory.isEmpty()) {
            System.out.println("No past orders for " + userName);
        } else {
            System.out.println("Order history for " + userName + ": " + orderHistory);
        }
    }


// Pour le test
     public void addOrder(String order) {
        orderHistory.add(order);
        System.out.println("Order '" + order + "' added to history for " + userName);
    }
}