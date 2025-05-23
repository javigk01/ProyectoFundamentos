package fundamentos.adcbank.controllers;

import fundamentos.adcbank.services.*;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;

public class TransferController {
    @FXML
    private TextField amountField;
    @FXML
    private TextField tokenField;
    @FXML
    private TextField targetAccountField;
    private AuthenticationService authService = AuthenticationService.getInstance();

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
                alert.setContentText("Transfer successful.");
                alert.showAndWait();
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

    private String getCurrentAccountId() {
        return "account1"; // Placeholder
    }
}