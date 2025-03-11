public class product {
    private String productname;
    private int productID;
    private double price;
    private int Stockquantity;

    public product(String productname, int productID, double price, int Stockquantity ) {
        this.productname = productname;
        this.productID = productID;
        this.price = price;
        this.Stockquantity = Stockquantity;
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
    public int getStockquantity() {
        return Stockquantity;
    }
    public void setStockquantity(int Stockquantity) {
        this.Stockquantity = Stockquantity;
    }
    public String updateStock(int quantity) {
        if (quantity > 0 && Stockquantity >= quantity) {
            this.Stockquantity -= quantity;
            return "Stock updated successfully. New stock quantity: " + Stockquantity;
        } else {
            return "Stock insufficient or invalid quantity.";
        }
    }
    public String getProductDetails() {
        return "Product ID: " + productID + ", Name: " + productname + ", Price: $" + price + ", Stock: " + Stockquantity;
    }
}