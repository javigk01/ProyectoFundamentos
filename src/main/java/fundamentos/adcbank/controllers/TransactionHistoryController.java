package fundamentos.adcbank.controllers;

import com.mongodb.client.MongoCollection;
import fundamentos.adcbank.services.AuthenticationService;
import fundamentos.adcbank.services.DatabaseService;
import fundamentos.adcbank.services.TransactionService;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.bson.Document;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @brief Controller for displaying transaction history in the ADCBank application.
 */
public class TransactionHistoryController {

    /** @brief ScrollPane containing the transaction list. */
    @FXML
    private ScrollPane scrollPane;

    /** @brief VBox container for transaction items. */
    @FXML
    private VBox transactionList;

    /** @brief Label showing account information. */
    @FXML
    private Label accountInfoLabel;

    /** @brief Label showing balance information. */
    @FXML
    private Label balanceInfoLabel;

    /** @brief Authentication service instance. */
    private AuthenticationService authService = AuthenticationService.getInstance();

    /** @brief Transaction service instance. */
    private TransactionService transactionService = TransactionService.getInstance();

    /**
     * @brief Initializes the controller and loads transaction history.
     */
    @FXML
    public void initialize() {
        loadTransactionHistory();
        setupAccountInfo();
    }

    /**
     * @brief Sets up account information display.
     */
    private void setupAccountInfo() {
        try {
            if (authService.getCurrentUser() != null) {
                String accountId = getCurrentAccountId();
                String username = authService.getCurrentUser().getString("username");
                double balance = getCurrentBalance();

                accountInfoLabel.setText("Account: " + username + " (" + accountId + ")");
                balanceInfoLabel.setText("Current Balance: $" + String.format("%.2f", balance));
            }
        } catch (Exception e) {
            accountInfoLabel.setText("Account information not available");
            balanceInfoLabel.setText("Balance: N/A");
        }
    }

    /**
     * @brief Loads and displays the transaction history.
     */
    private void loadTransactionHistory() {
        try {
            String accountId = getCurrentAccountId();
            if (accountId == null) {
                showNoTransactionsMessage();
                return;
            }

            List<Document> transactions = transactionService.getTransactionHistory(accountId);

            if (transactions.isEmpty()) {
                showNoTransactionsMessage();
                return;
            }

            for (Document transaction : transactions) {
                VBox transactionItem = createTransactionItem(transaction, accountId);
                transactionList.getChildren().add(transactionItem);
            }

        } catch (Exception e) {
            showErrorMessage("Error loading transaction history: " + e.getMessage());
        }
    }

    /**
     * @brief Creates a visual item for a transaction.
     * @param transaction The transaction document.
     * @param currentAccountId The current user's account ID.
     * @return VBox containing the transaction information.
     */
    private VBox createTransactionItem(Document transaction, String currentAccountId) {
        VBox item = new VBox(5);
        item.setAlignment(Pos.CENTER_LEFT);
        item.setPadding(new Insets(10));
        item.setStyle("-fx-background-color: #2a2a2a; -fx-background-radius: 8; -fx-border-color: #444; -fx-border-radius: 8;");

        // Transaction header
        HBox header = new HBox(10);
        header.setAlignment(Pos.CENTER_LEFT);

        String type = transaction.getString("type");
        String sourceAccountId = transaction.getString("accountId");
        String targetAccountId = transaction.getString("targetAccountId");
        double amount = transaction.getDouble("amount");
        Date timestamp = transaction.getDate("timestamp");
        String transactionId = transaction.getString("_id");

        // Determine if this is a sent or received transfer for the current user
        boolean isReceivedTransfer = "TRANSFER".equals(type) &&
                targetAccountId != null &&
                targetAccountId.equals(currentAccountId) &&
                !sourceAccountId.equals(currentAccountId);

        // Determine transaction display info
        String displayType = getDisplayType(type, isReceivedTransfer);
        String displayAmount = getDisplayAmount(type, amount, isReceivedTransfer);
        String amountColor = getAmountColor(type, isReceivedTransfer);

        Label typeLabel = new Label(displayType);
        typeLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px;");

        Label amountLabel = new Label(displayAmount);
        amountLabel.setStyle("-fx-text-fill: " + amountColor + "; -fx-font-weight: bold; -fx-font-size: 14px;");

        header.getChildren().addAll(typeLabel, amountLabel);

        // Transaction details
        VBox details = new VBox(3);

        // Add appropriate account information based on transaction type
        if ("TRANSFER".equals(type)) {
            if (isReceivedTransfer) {
                Label fromLabel = new Label("From Account: " + sourceAccountId);
                fromLabel.setStyle("-fx-text-fill: #888; -fx-font-size: 12px;");
                details.getChildren().add(fromLabel);
            } else {
                Label toLabel = new Label("To Account: " + targetAccountId);
                toLabel.setStyle("-fx-text-fill: #888; -fx-font-size: 12px;");
                details.getChildren().add(toLabel);
            }
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Label timestampLabel = new Label("Date: " + dateFormat.format(timestamp));
        timestampLabel.setStyle("-fx-text-fill: #888; -fx-font-size: 12px;");

        Label idLabel = new Label("Transaction ID: " + transactionId);
        idLabel.setStyle("-fx-text-fill: #666; -fx-font-size: 10px;");

        details.getChildren().addAll(timestampLabel, idLabel);

        item.getChildren().addAll(header, details);
        return item;
    }

    /**
     * @brief Gets the display type for a transaction.
     */
    private String getDisplayType(String type, boolean isReceivedTransfer) {
        switch (type) {
            case "DEPOSIT":
                return "💰 Deposit";
            case "WITHDRAW":
                return "🏧 Withdrawal";
            case "TRANSFER":
                return isReceivedTransfer ? "💰 Transfer Received" : "🔄 Transfer Sent";
            default:
                return "📊 Transaction";
        }
    }

    /**
     * @brief Gets the display amount with proper formatting.
     */
    private String getDisplayAmount(String type, double amount, boolean isReceivedTransfer) {
        if ("TRANSFER".equals(type) && isReceivedTransfer) {
            return "+$" + String.format("%.2f", amount);
        }

        if ("WITHDRAW".equals(type) || ("TRANSFER".equals(type) && !isReceivedTransfer)) {
            return "-$" + String.format("%.2f", amount);
        }

        return "+$" + String.format("%.2f", amount);
    }

    /**
     * @brief Gets the color for the amount display.
     */
    private String getAmountColor(String type, boolean isReceivedTransfer) {
        if ("TRANSFER".equals(type) && isReceivedTransfer) {
            return "#43c6ac"; // Green for received transfers
        }

        if ("WITHDRAW".equals(type) || ("TRANSFER".equals(type) && !isReceivedTransfer)) {
            return "#f56565"; // Red for outgoing
        }

        return "#43c6ac"; // Green for deposits
    }

    /**
     * @brief Shows a message when no transactions are found.
     */
    private void showNoTransactionsMessage() {
        Label noTransactions = new Label("No transactions found for this account.");
        noTransactions.setStyle("-fx-text-fill: #888; -fx-font-size: 16px; -fx-font-weight: bold;");
        noTransactions.setAlignment(Pos.CENTER);

        VBox container = new VBox(noTransactions);
        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(50));

        transactionList.getChildren().add(container);
    }

    /**
     * @brief Shows an error message.
     */
    private void showErrorMessage(String message) {
        Label errorLabel = new Label(message);
        errorLabel.setStyle("-fx-text-fill: #f56565; -fx-font-size: 14px;");
        errorLabel.setAlignment(Pos.CENTER);

        VBox container = new VBox(errorLabel);
        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(50));

        transactionList.getChildren().add(container);
    }

    /**
     * @brief Gets the current user's account ID.
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
            return account != null ? account.getString("_id") : null;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @brief Gets the current account balance.
     */
    private double getCurrentBalance() {
        try {
            String accountId = getCurrentAccountId();
            if (accountId == null) return 0.0;

            MongoCollection<Document> accounts = DatabaseService.getInstance()
                    .getDatabase()
                    .getCollection("accounts");
            Document account = accounts.find(new Document("_id", accountId)).first();

            if (account != null) {
                Object balanceValue = account.get("balance");
                if (balanceValue instanceof Number) {
                    return ((Number) balanceValue).doubleValue();
                }
            }
            return 0.0;
        } catch (Exception e) {
            return 0.0;
        }
    }

    /**
     * @brief Handles the close button action.
     */
    @FXML
    private void handleClose() {
        Stage stage = (Stage) transactionList.getScene().getWindow();
        stage.close();
    }
}