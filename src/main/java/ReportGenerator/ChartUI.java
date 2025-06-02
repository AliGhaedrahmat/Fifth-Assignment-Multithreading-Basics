package ReportGenerator;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ChartUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Sales Over Time");

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Year");
        yAxis.setLabel("Total Sales");

        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Total Sales by Year");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Sales");

        for (ReportData data : TaskRunnable.reportResults) {
            series.getData().add(new XYChart.Data<>(data.year, data.totalCost));
        }

        lineChart.getData().add(series);

        StackPane root = new StackPane(lineChart);
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }
}
