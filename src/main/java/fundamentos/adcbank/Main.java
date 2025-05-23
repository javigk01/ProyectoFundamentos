package fundamentos.adcbank;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Update the path to match the actual location of LoginView.fxml
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/fundamentos/adcbank/LoginView.fxml"));
        if (loader.getLocation() == null) {
            throw new IllegalStateException("FXML file not found: /fundamentos/adcbank/LoginView.fxml");
        }
        primaryStage.setScene(new Scene(loader.load()));
        primaryStage.setTitle("Bank App");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}