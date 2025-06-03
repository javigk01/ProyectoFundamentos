package fundamentos.adcbank.controllers;

import fundamentos.adcbank.services.AuthenticationService;
import fundamentos.adcbank.services.DatabaseService;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import java.util.Observable;
import java.util.Observer;

/**
 * @brief Controller for the main UI view of the ADCBank application.
 */
public class MainController implements Observer {

    /** @brief Label displaying the welcome message with username. */
    @FXML
    private Label welcomeLabel;

    /** @brief Label displaying the account balance. */
    @FXML
    private Label balanceLabel;

    /** @brief Label displaying the authentication token. */
    @FXML
    private Label tokenLabel;

    /** @brief Button for initiating a deposit action. */
    @FXML
    private Button depositButton;

    /** @brief Button for initiating a withdrawal action. */
    @FXML
    private Button withdrawButton;

    /** @brief Button for initiating a transfer action. */
    @FXML
    private Button transferButton;

    /** @brief Button for logging out the user. */
    @FXML
    private Button logoutButton;

    /** @brief Authentication service instance for user and balance management. */
    private AuthenticationService authService = AuthenticationService.getInstance();

    /**
     * @brief Initializes the controller and sets up the UI with user data.
     */
    @FXML
    public void initialize() {
        authService.addObserver(this);
        if (authService.getCurrentUser() != null) {
            // Set welcome message with username
            String username = authService.getCurrentUser().getString("username");
            welcomeLabel.setText("Welcome, " + username + "!");

            // Set token and refresh balance
            tokenLabel.setText(authService.getCurrentToken());
            refreshBalance();
        }
    }

    /**
     * @brief Retrieves the account ID for a given user.
     * @param userId The ID of the user.
     * @return The account ID, or a default value if not found.
     */
    private String getAccountIdForUser(String userId) {
        MongoCollection<Document> accounts = DatabaseService.getInstance().getDatabase().getCollection("accounts");
        Document query = new Document("userId", userId);
        Document account = accounts.find(query).first();
        return account != null ? account.getString("_id") : "account1";
    }

    /**
     * @brief Retrieves the balance for a given account.
     * @param accountId The ID of the account.
     * @return The account balance, or 0.0 if not found or invalid.
     */
    private double getBalanceForAccount(String accountId) {
        MongoCollection<Document> accounts = DatabaseService.getInstance().getDatabase().getCollection("accounts");
        Document query = new Document("_id", accountId);
        Document account = accounts.find(query).first();
        if (account != null) {
            Object balanceValue = account.get("balance");
            if (balanceValue instanceof Integer) {
                return ((Integer) balanceValue).doubleValue();
            } else if (balanceValue instanceof Double) {
                return (Double) balanceValue;
            } else if (balanceValue instanceof Number) {
                return ((Number) balanceValue).doubleValue();
            }
        }
        return 0.0;
    }

    /**
     * @brief Handles the deposit action by opening the deposit view.
     */
    @FXML
    private void handleDeposit() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fundamentos/adcbank/DepositView.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("ADC Bank - Deposit");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @brief Handles the withdrawal action by opening the withdrawal view.
     */
    @FXML
    private void handleWithdraw() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fundamentos/adcbank/WithdrawView.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("ADC Bank - Withdraw");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @brief Handles the transfer action by opening the transfer view.
     */
    @FXML
    private void handleTransfer() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fundamentos/adcbank/TransferView.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("ADC Bank - Transfer");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @brief Handles the logout action and navigates to the login view.
     */
    @FXML
    private void handleLogout() {
        // Clear UI elements
        balanceLabel.setText("0.00");
        tokenLabel.setText("");
        welcomeLabel.setText("");

        // Logout from service
        authService.logout();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fundamentos/adcbank/LoginView.fxml"));
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("ADC Bank - Login");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @brief Updates the UI when notified by the authentication service.
     * @param o The observable object.
     * @param arg The notification argument.
     */
    @Override
    public void update(Observable o, Object arg) {
        if (o == authService) {
            if (arg instanceof String) {
                String message = (String) arg;
                if (message.equals("balance_update")) {
                    refreshBalance();
                } else {
                    tokenLabel.setText(message);
                }
            }
        }
    }

    /**
     * @brief Refreshes the balance displayed in the UI.
     */
    private void refreshBalance() {
        if (authService.getCurrentUser() != null) {
            String accountId = getAccountIdForUser(authService.getCurrentUser().getString("_id"));
            double balance = getBalanceForAccount(accountId);
            balanceLabel.setText(String.format("%.2f", balance));
        }
    }
}