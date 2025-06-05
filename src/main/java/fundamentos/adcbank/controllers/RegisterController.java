package fundamentos.adcbank.controllers;

import fundamentos.adcbank.services.EmailVerificationService;
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

    /** @brief Email verification service instance. */
    private EmailVerificationService verificationService = EmailVerificationService.getInstance();

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

        // Validate password strength
        if (password.length() < 6) {
            showAlert(Alert.AlertType.ERROR, "Weak Password", "Password must be at least 6 characters long.");
            return;
        }

        // Send verification email
        boolean codeSent = verificationService.sendVerificationCode(email, username);

        if (codeSent) {
            showAlert(Alert.AlertType.INFORMATION, "Verification Required",
                    "A verification code has been sent to your email address. Please check your inbox.");

            // Navigate to email verification view
            navigateToEmailVerification(email, username, password);
        } else {
            showAlert(Alert.AlertType.ERROR, "Email Error",
                    "Failed to send verification email. Please check your email address and try again.");
        }
    }

    /**
     * @brief Navigates to the email verification view.
     * @param email The email address.
     * @param username The username.
     * @param password The password.
     */
    private void navigateToEmailVerification(String email, String username, String password) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fundamentos/adcbank/EmailVerificationView.fxml"));
            Stage stage = (Stage) usernameField.getScene().getWindow();
            Scene scene = new Scene(loader.load());

            // Get the controller and initialize it with user data
            EmailVerificationController controller = loader.getController();
            controller.initializeWithData(email, username, password);

            stage.setScene(scene);
            stage.setTitle("ADC Bank - Email Verification");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Failed to navigate to verification page.");
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
     * @brief Navigates back to the login view.
     */
    @FXML
    private void handleBackToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fundamentos/adcbank/LoginView.fxml"));
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("ADC Bank - Login");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}