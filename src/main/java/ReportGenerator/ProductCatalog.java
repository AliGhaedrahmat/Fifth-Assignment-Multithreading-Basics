package ReportGenerator;
import java.io.*;
import java.util.*;
import Utils.*;
import Utils.Console;

public class ProductCatalog {

    private static final Map<String, Product> products = new HashMap<>();
    public static void loadProducts(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(ProductCatalog.class.getClassLoader().getResource(filename).getFile()))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String id = parts[0].trim();
                    String name = parts[1].trim();
                    double price = Double.parseDouble(parts[2].trim());
                    products.put(id, new Product(id, name, price));
                }
            }
        }
        catch (IOException ex) {
            Console.print("ERROR in loadProducts() - ProductCatalog class: " + ex.getMessage() , Console.Color.RED);
        }
    }

    public static Product getProduct(String id) {
        return products.get(id);
    }

}
