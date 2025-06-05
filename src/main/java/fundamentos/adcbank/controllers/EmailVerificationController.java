package fundamentos.adcbank.controllers;

import fundamentos.adcbank.services.AuthenticationService;
import fundamentos.adcbank.services.EmailVerificationService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * @brief Controller for handling email verification in the ADCBank application.
 */
public class EmailVerificationController {

    /** @brief Text field for entering the verification code. */
    @FXML
    private TextField verificationCodeField;

    /** @brief Label displaying the email address. */
    @FXML
    private Label emailDisplayLabel;

    /** @brief Email verification service instance. */
    private EmailVerificationService verificationService = EmailVerificationService.getInstance();

    /** @brief Authentication service instance. */
    private AuthenticationService authService = AuthenticationService.getInstance();

    /** @brief Email address being verified. */
    private String email;

    /** @brief Username associated with the email. */
    private String username;

    /** @brief Password for the account. */
    private String password;

    /**
     * @brief Initializes the controller with user data.
     * @param email The email address being verified.
     * @param username The username for the account.
     * @param password The password for the account.
     */
    public void initializeWithData(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;

        // Display the email address (partially masked for security)
        emailDisplayLabel.setText(maskEmail(email));

        // Focus on the verification code field
        Platform.runLater(() -> verificationCodeField.requestFocus());
    }

    /**
     * @brief Handles the verify button action.
     */
    @FXML
    private void handleVerify() {
        String code = verificationCodeField.getText().trim();

        if (code.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Input Required", "Please enter the verification code.");
            return;
        }

        if (code.length() != 6) {
            showAlert(Alert.AlertType.ERROR, "Invalid Code", "Verification code must be 6 digits.");
            return;
        }

        if (!code.matches("\\d{6}")) {
            showAlert(Alert.AlertType.ERROR, "Invalid Code", "Verification code must contain only numbers.");
            return;
        }

        // Verify the code
        if (verificationService.verifyCode(email, code)) {
            // Code is valid, proceed with registration
            boolean registered = authService.register(username, email, password);

            if (registered) {
                showAlert(Alert.AlertType.INFORMATION, "Success",
                        "Account created successfully! You can now log in.");
                navigateToLogin();
            } else {
                showAlert(Alert.AlertType.ERROR, "Registration Failed",
                        "Failed to create account. Username or email may already exist.");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Invalid Code",
                    "The verification code is invalid or has expired. Please try again.");
            verificationCodeField.clear();
            verificationCodeField.requestFocus();
        }
    }

    /**
     * @brief Handles the resend code button action.
     */
    @FXML
    private void handleResendCode() {
        boolean sent = verificationService.sendVerificationCode(email, username);

        if (sent) {
            showAlert(Alert.AlertType.INFORMATION, "Code Sent",
                    "A new verification code has been sent to your email address.");
            verificationCodeField.clear();
            verificationCodeField.requestFocus();
        } else {
            showAlert(Alert.AlertType.ERROR, "Send Failed",
                    "Failed to send verification code. Please try again later.");
        }
    }

    /**
     * @brief Handles the cancel button action.
     */
    @FXML
    private void handleCancel() {
        navigateToRegister();
    }

    /**
     * @brief Navigates to the login view.
     */
    private void navigateToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fundamentos/adcbank/LoginView.fxml"));
            Stage stage = (Stage) verificationCodeField.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("ADC Bank - Login");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @brief Navigates to the register view.
     */
    private void navigateToRegister() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fundamentos/adcbank/RegisterView.fxml"));
            Stage stage = (Stage) verificationCodeField.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("ADC Bank - Register");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @brief Masks an email address for display purposes.
     * @param email The email address to mask.
     * @return The masked email address.
     */
    private String maskEmail(String email) {
        if (email == null || !email.contains("@")) {
            return email;
        }

        String[] parts = email.split("@");
        String localPart = parts[0];
        String domain = parts[1];

        if (localPart.length() <= 3) {
            return email; // Don't mask very short email addresses
        }

        String maskedLocal = localPart.substring(0, 2) + "***" + localPart.substring(localPart.length() - 1);
        return maskedLocal + "@" + domain;
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
}