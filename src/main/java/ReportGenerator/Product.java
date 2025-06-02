package ReportGenerator;

public class Product {
    private String productId;
    private String productName;
    private double price;

    public Product(String productId, String productName, double price) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
    }

    public String getProductIdId() { return productId; }
    public String getProductName() { return productName; }
    public double getPrice() { return price; }
}
