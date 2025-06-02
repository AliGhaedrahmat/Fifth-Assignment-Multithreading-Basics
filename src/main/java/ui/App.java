package ui;

import Utils.Console;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class App extends Application {



    @Override
    public void start(Stage stage) {

        try {
            URL fxmlUrl = getClass().getClassLoader().getResource("ui/TypingTest.fxml");
            System.out.println("FXML URL: " + fxmlUrl);
            assert fxmlUrl != null;
            Parent root = FXMLLoader.load(fxmlUrl);
            Scene scene = new Scene(root);
            stage.setTitle("App");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();  // print full details
        }
    }

    public static void run() {
        launch();
    }
}
