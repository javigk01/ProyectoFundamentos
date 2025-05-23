package fundamentos.adcbank.controllers;

import com.mongodb.client.MongoCollection;
import fundamentos.adcbank.models.Account;
import fundamentos.adcbank.services.*;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.bson.Document;

public class DepositController {
    @FXML
    private TextField amountField;
    @FXML
    private TextField tokenField;
    private AuthenticationService authService = AuthenticationService.getInstance();

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

                // Debug log
                System.out.println("Deposit successful for account: " + accountId);

                // Refresh UI
                authService.notifyBalanceUpdate();

                // Show success and close
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Deposit successful.");
                alert.showAndWait();

                Stage stage = (Stage) amountField.getScene().getWindow();
                stage.close();
            } else {
                System.out.println("Token validation failed"); // Debug log
                showErrorAlert("Invalid token.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount format: " + e.getMessage()); // Debug log
            showErrorAlert("Please enter a valid amount.");
        } catch (Exception e) {
            System.out.println("Deposit error: " + e.getMessage()); // Debug log
            e.printStackTrace();
            showErrorAlert("An unexpected error occurred.");
        }
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private String getCurrentAccountId() {
        try {
            // Verify user is logged in
            if (authService.getCurrentUser() == null) {
                System.out.println("No user logged in");
                return null;
            }

            String userId = authService.getCurrentUser().getString("_id");
            System.out.println("Current user ID: " + userId); // Debug

            MongoCollection<Document> accounts = DatabaseService.getInstance()
                    .getDatabase()
                    .getCollection("accounts");

            // Find account for this user
            Document query = new Document("userId", userId);
            Document account = accounts.find(query).first();

            if (account == null) {
                System.out.println("No account found for user: " + userId);
                // Create a new account if none exists
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
            System.out.println("Error getting account ID: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}