package fundamentos.adcbank.controllers;

import fundamentos.adcbank.services.AuthenticationService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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

    /** @brief Text field for entering the email. */
    @FXML
    private TextField emailField;

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
        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText();

        // Validate input fields
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "All fields are required.");
            return;
        }

        // Basic email validation
        if (!isValidEmail(email)) {
            showAlert(Alert.AlertType.ERROR, "Invalid Email", "Please enter a valid email address.");
            return;
        }

        boolean registered = authService.register(username, email, password);
        if (registered) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "User registered successfully!");
            handleBackToLogin();
        } else {
            showAlert(Alert.AlertType.ERROR, "Registration Failed", "Username or email already exists.");
        }
    }

    /**
     * @brief Validates email format using a simple regex pattern.
     * @param email The email to validate.
     * @return True if email format is valid, false otherwise.
     */
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(emailRegex);
    }

    /**
     * @brief Shows an alert dialog with the specified parameters.
     * @param type The type of alert.
     * @param title The title of the alert.
     * @param message The message to display.
     */
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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