package fundamentos.adcbank.controllers;

import com.mongodb.client.MongoCollection;
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
            TransactionValidator tokenValidator = new TokenValidator(authService);
            if (tokenValidator.validate(accountId, amount, null, token)) {
                TransactionCommand command = new DepositCommand();
                command.execute(accountId, amount, null);
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
                alert.setContentText("Invalid token.");
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

    private String getCurrentAccountId() {
        String userId = authService.getCurrentUser().getString("_id");
        MongoCollection<Document> accounts = DatabaseService.getInstance().getDatabase().getCollection("accounts");
        Document query = new Document("userId", userId);
        Document account = accounts.find(query).first();
        return account != null ? account.getString("_id") : null;
    }
}