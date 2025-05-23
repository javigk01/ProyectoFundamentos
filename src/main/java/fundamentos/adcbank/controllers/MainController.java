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

public class MainController implements Observer {
    @FXML
    private Label balanceLabel;
    @FXML
    private Label tokenLabel;
    @FXML
    private Button depositButton;
    @FXML
    private Button withdrawButton;
    @FXML
    private Button transferButton;
    @FXML
    private Button logoutButton;
    private AuthenticationService authService = AuthenticationService.getInstance();


    @FXML
    public void initialize() {
        authService.addObserver(this);
        tokenLabel.setText(authService.getCurrentToken());
        String accountId = getAccountIdForUser(authService.getCurrentUser().getString("_id"));
        double balance = getBalanceForAccount(accountId);
        balanceLabel.setText(String.format("%.2f", balance));
    }

    private String getAccountIdForUser(String userId) {
        MongoCollection<Document> accounts = DatabaseService.getInstance().getDatabase().getCollection("accounts");
        Document query = new Document("userId", userId);
        Document account = accounts.find(query).first();
        return account != null ? account.getString("_id") : "account1";
    }

    private double getBalanceForAccount(String accountId) {
        MongoCollection<Document> accounts = DatabaseService.getInstance().getDatabase().getCollection("accounts");
        Document query = new Document("_id", accountId);
        Document account = accounts.find(query).first();

        // Updated balance retrieval to handle both Integer and Double cases
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
        return 0.0; // Default value if not found or invalid type
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o == authService) {
            tokenLabel.setText((String) arg);
        }
    }

    @FXML
    private void handleDeposit() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fundamentos/adcbank/DepositView.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleWithdraw() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fundamentos/adcbank/WithdrawView.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleTransfer() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fundamentos/adcbank/TransferView.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogout() {
        authService.logout();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fundamentos/adcbank/LoginView.fxml"));
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}