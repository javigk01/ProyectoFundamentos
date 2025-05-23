package fundamentos.adcbank.controllers;

import fundamentos.adcbank.services.*;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;

public class WithdrawController {
    @FXML
    private TextField amountField;
    @FXML
    private TextField tokenField;
    private AuthenticationService authService = AuthenticationService.getInstance();

    @FXML
    private void handleWithdraw() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            String token = tokenField.getText();
            String accountId = getCurrentAccountId();
            TransactionValidator tokenValidator = new TokenValidator(authService);
            TransactionValidator balanceValidator = new BalanceValidator();
            tokenValidator.setNext(balanceValidator);
            if (tokenValidator.validate(accountId, amount, null, token)) {
                TransactionCommand command = new WithdrawCommand();
                command.execute(accountId, amount, null);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Withdrawal successful.");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Validation Failed");
                alert.setHeaderText(null);
                alert.setContentText("Invalid token or insufficient balance.");
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