package ReportGenerator;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import Utils.Console;

public class TaskRunnable implements Runnable {
    private final String filename;
    private static final DecimalFormat df = new DecimalFormat("#.00");
    public static List<ReportData> reportResults = Collections.synchronizedList(new ArrayList<>());

    public TaskRunnable(String filename) {
        this.filename = filename;
    }

    @Override
    public void run() {
        double totalCost = 0.0;
        int totalItems = 0;
        double totalDiscount = 0.0;
        double mostExpensivePurchase = 0.0;

        try (BufferedReader br = new BufferedReader(new FileReader(getClass().getClassLoader().getResource(filename).getFile()))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String productId = parts[0].trim();
                    int quantity = Integer.parseInt(parts[1].trim());
                    double discountPercent = Double.parseDouble(parts[2].trim());

                    Product product = ProductCatalog.getProduct(productId);
                    if (product != null) {
                        double price = product.getPrice();
                        double discountedPrice = price * (1 - discountPercent / 100);
                        double purchaseCost = discountedPrice * quantity;

                        totalCost += purchaseCost;
                        totalItems += quantity;
                        totalDiscount += discountPercent * quantity;

                        if (discountedPrice > mostExpensivePurchase) {
                            mostExpensivePurchase = discountedPrice;
                        }
                    } else {
                        System.err.println("Product not found: " + productId);
                    }
                }
            }
        } catch (IOException e) {
            Console.print("Error reading file: " + filename , Console.Color.RED);
        }

        double averageDiscount = totalItems > 0 ? totalDiscount / totalItems : 0.0;

        String reportString = "";
        reportString += "\nTotal Cost: " + df.format(totalCost);
        reportString += "\nTotal Items Bought: " + totalItems;
        reportString += "\nAverage Discount: " + df.format(averageDiscount) + "%";
        reportString += "\nMost Expensive Purchase After Discount: " + df.format(mostExpensivePurchase);

        Console.print("Report for " + filename + ":" , Console.Color.GREEN);
        Console.print(reportString , Console.Color.YELLOW);
        Console.print("-----------------------------------------------------------" , Console.Color.YELLOW);


        String year = filename.substring(0, 4);
        reportResults.add(new ReportData(year, totalCost));
    }
}
