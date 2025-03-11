public class product {
    private String productname;
    private int productID;
    private double price;
    private int quantity;

    public product(String productname, int productID, double price, int quantity ) {
        this.productname = productname;
        this.productID = productID;
        this.price = price;
        this.quantity = quantity;
    }
    public String getProductname() {
        return productname;
    }
    public void setProductname(String productname) {
        this.productname = productname;
    }
    public int getProductID() {
        return productID;
    }
    public void setProductID(int productID) {
        this.productID = productID;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}