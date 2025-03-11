public class order {
    private String orderID;
    private String user;
    private int items;
    private boolean status;

    public order(String orderID, String user, int items, boolean status ) {
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
    public int getItems() {
        return items;
    }
    public void setItems(int items) {
        this.items = items;
    }
    public boolean  getStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }
}