package ReportGenerator;

import javafx.application.Application;
import Utils.Console;

import java.io.IOException;

public class ReportGenerator {

    public static void run() throws InterruptedException {
        Console.clear();
        Console.print("Report Generator running..." , Console.Color.BLUE);
        Console.print("Results : " , Console.Color.BLUE);

        ProductCatalog.loadProducts("Products.txt");

        String[] orderFiles = {
                "2021_order_details.txt",
                "2022_order_details.txt",
                "2023_order_details.txt",
                "2024_order_details.txt"
        };

        Thread[] threads = new Thread[orderFiles.length];

        for (int i = 0; i < orderFiles.length; i++) {
            threads[i] = new Thread(new TaskRunnable(orderFiles[i]));
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }


        Application.launch(ChartUI.class);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        run();
    }
}
