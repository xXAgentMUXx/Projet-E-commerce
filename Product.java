public class Product {
    private String productName;
    private int productID;
    private double price;
    private int stockQuantity;

    public Product(String productName, int productID, double price, int stockQuantity) {
        this.productName = productName;
        this.productID = productID;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }


    public void updateStock(int quantity) {
        if (quantity > 0 && stockQuantity >= quantity) {
            this.stockQuantity -= quantity;
        } else {
            System.out.println("Stock insuffisant ou quantité invalide.");
        }
    }

    public String getProductDetails() {
        return "Product ID: " + productID + ", Name: " + productName + ", Price: $" + price + ", Stock: " + stockQuantity;
    }
}