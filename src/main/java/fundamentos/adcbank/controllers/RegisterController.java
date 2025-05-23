package fundamentos.adcbank.controllers;

import fundamentos.adcbank.services.AuthenticationService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * @brief Controller for handling user registration in the ADCBank application.
 */
public class RegisterController {

    /** @brief Text field for entering the username. */
    @FXML
    private TextField usernameField;

    /** @brief Password field for entering the password. */
    @FXML
    private PasswordField passwordField;

    /** @brief Authentication service instance for handling registration logic. */
    private AuthenticationService authService = AuthenticationService.getInstance();

    /**
     * @brief Handles the user registration process.
     */
    @FXML
    private void handleRegister() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            System.out.println("Username and password cannot be empty.");
            return;
        }

        boolean registered = authService.register(username, password);
        if (registered) {
            System.out.println("User registered successfully!");
            handleBackToLogin();
        } else {
            System.out.println("Registration failed: Username already exists.");
        }
    }

    /**
     * @brief Navigates back to the login view after successful registration.
     */
    @FXML
    private void handleBackToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fundamentos/adcbank/LoginView.fxml"));
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}