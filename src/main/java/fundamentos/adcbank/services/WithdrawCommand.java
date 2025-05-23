package fundamentos.adcbank.services;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import java.util.Date;

public class WithdrawCommand implements TransactionCommand {
    @Override
    public void execute(String accountId, double amount, String targetAccountId) {
        try {
            System.out.println("Executing withdrawal from: " + accountId);

            MongoCollection<Document> accounts = DatabaseService.getInstance()
                    .getDatabase()
                    .getCollection("accounts");

            // Ensure uppercase
            Document query = new Document("_id", accountId.toUpperCase());
            Document account = accounts.find(query).first();

            if (account == null) {
                System.out.println("Account not found for withdrawal: " + accountId);
                throw new RuntimeException("Account not found");
            }

            Object balanceValue = account.get("balance");
            double balance = convertBalance(balanceValue);

            System.out.println("Current balance: " + balance + ", Withdrawing: " + amount);

            if (balance < amount) {
                throw new RuntimeException("Insufficient funds");
            }

            accounts.updateOne(query,
                    new Document("$set", new Document("balance", balance - amount)));

            System.out.println("Withdrawal successful. New balance: " + (balance - amount));
        } catch (Exception e) {
            System.out.println("Withdrawal failed: " + e.getMessage());
            throw e;
        }
    }

    private double convertBalance(Object balanceValue) {
        if (balanceValue instanceof Integer) {
            return ((Integer) balanceValue).doubleValue();
        } else if (balanceValue instanceof Double) {
            return (Double) balanceValue;
        }
        throw new IllegalArgumentException("Invalid balance type");
    }
}