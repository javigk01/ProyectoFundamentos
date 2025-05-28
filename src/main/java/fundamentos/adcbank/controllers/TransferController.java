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
            //Data on regards of users transaction data
            double amount = Double.parseDouble(amountField.getText());
            String token = tokenField.getText();
            //the user who sends and who the user is sending to
            String targetAccountId = targetAccountField.getText();
            String accountId = getCurrentAccountId();
            //Validations
            TransactionValidator tokenValidator = new TokenValidator(authService);
            TransactionValidator balanceValidator = new BalanceValidator();
            TransactionValidator accountExistsValidator = new AccountExistsValidator();
            tokenValidator.setNext(balanceValidator);
            balanceValidator.setNext(accountExistsValidator);
            //If transaction gets validated
            if (tokenValidator.validate(accountId, amount, targetAccountId, token)) {
                //Execute transfer
                TransactionCommand command = new TransferCommand();
                command.execute(accountId, amount, targetAccountId);
                //Indicate user on regards of the success of the operation
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Transaction successful.");
                alert.showAndWait();
                Stage stage = (Stage) amountField.getScene().getWindow();
                stage.close();
                //Shows new balance and makes a notification about the balance updating
                authService.notifyBalanceUpdate();
                AuthenticationService.getInstance().notifyBalanceUpdate();
                //If not validated
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Validation Failed");
                alert.setHeaderText(null);
                //Reasons for failure
                alert.setContentText("Invalid token, insufficient balance, or target account does not exist.");
                alert.showAndWait();
            }
            
        } catch (NumberFormatException e) {
            //Errors on user input (format)
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
            //If current user is null
            if (authService.getCurrentUser() == null) {
                return null;
            }
            
            String userId = authService.getCurrentUser().getString("_id");
            //Gets account information from the database
            MongoCollection<Document> accounts = DatabaseService.getInstance()
                    .getDatabase()
                    .getCollection("accounts");
            Document query = new Document("userId", userId);
            Document account = accounts.find(query).first();
            
            //Creates the account if not found
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
