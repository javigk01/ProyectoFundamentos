package fundamentos.adcbank.controllers;

import com.mongodb.client.MongoCollection;
import fundamentos.adcbank.models.Account;
import fundamentos.adcbank.services.*;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.bson.Document;

/**
 * @brief Controller for handling deposit operations in the ADCBank application.
 */
public class DepositController {

    /** @brief Text field for entering the deposit amount. */
    @FXML
    private TextField amountField;

    /** @brief Text field for entering the authentication token. */
    @FXML
    private TextField tokenField;

    /** @brief Authentication service instance for user and token validation. */
    private AuthenticationService authService = AuthenticationService.getInstance();

    /**
     * @brief Handles the deposit action triggered from the UI.
     */
    @FXML
    private void handleDeposit() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            String token = tokenField.getText();
            String accountId = getCurrentAccountId();

            if (accountId == null) {
                showErrorAlert("No account available for deposit");
                return;
            }

            TransactionValidator tokenValidator = new TokenValidator(authService);
            if (tokenValidator.validate(accountId, amount, null, token)) {
                TransactionCommand command = new DepositCommand();
                command.execute(accountId, amount, null);
                authService.notifyBalanceUpdate();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Deposit successful.");
                alert.showAndWait();
                Stage stage = (Stage) amountField.getScene().getWindow();
                stage.close();
            } else {
                showErrorAlert("Invalid token.");
            }
        } catch (NumberFormatException e) {
            showErrorAlert("Please enter a valid amount.");
        } catch (Exception e) {
            showErrorAlert("An unexpected error occurred.");
        }
    }

    /**
     * @brief Displays an error alert with the specified message.
     * @param message The error message to display.
     */
    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * @brief Retrieves the current user's account ID, creating an account if necessary.
     * @return The account ID as a String, or null if not available.
     */
    private String getCurrentAccountId() {
        try {
            if (authService.getCurrentUser() == null) {
                return null;
            }
            String userId = authService.getCurrentUser().getString("_id");
            MongoCollection<Document> accounts = DatabaseService.getInstance()
                    .getDatabase()
                    .getCollection("accounts");
            Document query = new Document("userId", userId);
            Document account = accounts.find(query).first();
            if (account == null) {
                Account newAccount = new AccountFactory().createAccount("checking", userId);
                Document accountDoc = new Document()
                        .append("userId", userId)
                        .append("type", "checking")
                        .append("balance", 0.0);
                accounts.insertOne(accountDoc);
                return accountDoc.getString("_id");
            }
            return account.getString("_id");
        } catch (Exception e) {
            return null;
        }
    }
}