import java.util.List;

public class Order {
    private String orderID;
    private String user;
    private List<String> items;
    private String status;

    public Order(String orderID, String user, List<String>items, String status ) {
        this.orderID = orderID;
        this.user = user;
        this.items = items;
        this.status = status;
    }
    public String getOrderID() {
        return orderID;
    }
    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public List<String> getItems() {
        return items;
    }
    public void setItems(List<String> items) {
        this.items = items;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String updateStatus(String newStatus) {
        this.status = newStatus;
        return "Order " + orderID + " status updated to: " + status;
    }
    public String placeOrder() {
        StringBuilder result = new StringBuilder();
        result.append("Order ").append(orderID).append(" placed by ").append(user).append(".\n");
        for (String item : items) {
            result.append("Updating stock for ").append(item).append("...\n");
        }
        return result.toString();
    }
    public static void main(String[] args) {
        List<String> items = List.of("Item1", "Item2", "Item3");  
        Order order = new Order("12345", "John Doe", items, "processing");

        String orderConfirmation = order.placeOrder();
        System.out.println(orderConfirmation);
        String statusUpdate = order.updateStatus("shipping");
        System.out.println(statusUpdate);
    }
}