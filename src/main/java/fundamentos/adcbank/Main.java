/**
 * @file Main.java
 * @brief Entry point for the ADCBank JavaFX application.
 */
package fundamentos.adcbank;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @class Main
 * @brief Main application class for ADCBank.
 */
public class Main extends Application {

    /**
     * Starts the JavaFX application and loads the login view.
     * @param primaryStage The primary stage for this application.z
     * @throws Exception if the FXML file cannot be loaded.
     */
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

    /**
     * Main method. Launches the JavaFX application.
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }
}