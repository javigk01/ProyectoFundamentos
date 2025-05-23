package fundamentos.adcbank.controllers;

import fundamentos.adcbank.services.AuthenticationService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * @brief Controller for handling user login in the ADCBank application.
 */
public class LoginController {

    /** @brief Text field for entering the username. */
    @FXML
    private TextField usernameField;

    /** @brief Text field for entering the password. */
    @FXML
    private TextField passwordField;

    /** @brief Authentication service instance for login validation. */
    private AuthenticationService authService = AuthenticationService.getInstance();

    /**
     * @brief Handles the login action and navigates to the main view on success.
     */
    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (authService.login(username, password)) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fundamentos/adcbank/MainView.fxml"));
                Stage stage = (Stage) usernameField.getScene().getWindow();
                stage.setScene(new Scene(loader.load()));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @brief Navigates to the registration view.
     */
    @FXML
    private void handleRegister() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fundamentos/adcbank/RegisterView.fxml"));
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}