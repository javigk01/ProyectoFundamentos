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
 * @brief Controller for handling transfer operations in the ADCBank application.
 */
public class TransferController {

    /** @brief Text field for entering the transfer amount. */
    @FXML
    private TextField amountField;

    /** @brief Text field for entering the authentication token. */
    @FXML
    private TextField tokenField;

    /** @brief Text field for entering the target account ID. */
    @FXML
    private TextField targetAccountField;

    /** @brief Authentication service instance for user and balance validation. */
    private AuthenticationService authService = AuthenticationService.getInstance();

    /**
     * @brief Handles the transfer action triggered from the UI.
     */
    @FXML
    private void handleTransfer() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            String token = tokenField.getText();
            String targetAccountId = targetAccountField.getText();
            String accountId = getCurrentAccountId();
            TransactionValidator tokenValidator = new TokenValidator(authService);
            TransactionValidator balanceValidator = new BalanceValidator();
            TransactionValidator accountExistsValidator = new AccountExistsValidator();
            tokenValidator.setNext(balanceValidator);
            balanceValidator.setNext(accountExistsValidator);
            if (tokenValidator.validate(accountId, amount, targetAccountId, token)) {
                TransactionCommand command = new TransferCommand();
                command.execute(accountId, amount, targetAccountId);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Transaction successful.");
                alert.showAndWait();
                Stage stage = (Stage) amountField.getScene().getWindow();
                stage.close();
                authService.notifyBalanceUpdate();
                AuthenticationService.getInstance().notifyBalanceUpdate();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Validation Failed");
                alert.setHeaderText(null);
                alert.setContentText("Invalid token, insufficient balance, or target account does not exist.");
                alert.showAndWait();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid amount.");
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
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