package fundamentos.adcbank.services;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class BalanceValidator extends TransactionValidator {
    @Override
    public boolean validate(String accountId, double amount, String targetAccountId, String token) {
        try {
            MongoCollection<Document> accounts = DatabaseService.getInstance()
                    .getDatabase()
                    .getCollection("accounts");

            // Ensure uppercase account ID matching
            Document account = accounts.find(new Document("_id", accountId.toUpperCase())).first();

            if (account == null) {
                System.out.println("Account not found: " + accountId);
                return false;
            }

            // Robust balance extraction
            Object balanceValue = account.get("balance");
            double balance;

            if (balanceValue instanceof Integer) {
                balance = ((Integer) balanceValue).doubleValue();
            } else if (balanceValue instanceof Double) {
                balance = (Double) balanceValue;
            } else if (balanceValue instanceof Long) {
                balance = ((Long) balanceValue).doubleValue();
            } else {
                System.out.println("Invalid balance type: " + balanceValue.getClass());
                return false;
            }

            System.out.println("Current balance: " + balance + ", Attempting to withdraw: " + amount);

            if (balance < amount) {
                System.out.println("Insufficient funds");
                return false;
            }

            return next == null || next.validate(accountId, amount, targetAccountId, token);
        } catch (Exception e) {
            System.out.println("Validation error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}