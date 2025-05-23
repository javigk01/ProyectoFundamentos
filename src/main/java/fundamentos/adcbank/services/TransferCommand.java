package fundamentos.adcbank.services;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class TransferCommand implements TransactionCommand {
    @Override
    public void execute(String sourceAccountId, double amount, String targetAccountId) {
        MongoCollection<Document> accounts = DatabaseService.getInstance().getDatabase().getCollection("accounts");

        // Withdraw from source
        Document sourceQuery = new Document("_id", sourceAccountId);
        Document sourceAccount = accounts.find(sourceQuery).first();
        if (sourceAccount != null) {
            Object sourceBalanceValue = sourceAccount.get("balance");
            double sourceBalance = convertBalance(sourceBalanceValue);
            accounts.updateOne(sourceQuery, new Document("$set", new Document("balance", sourceBalance - amount)));
        }

        // Deposit to target
        Document targetQuery = new Document("_id", targetAccountId);
        Document targetAccount = accounts.find(targetQuery).first();
        if (targetAccount != null) {
            Object targetBalanceValue = targetAccount.get("balance");
            double targetBalance = convertBalance(targetBalanceValue);
            accounts.updateOne(targetQuery, new Document("$set", new Document("balance", targetBalance + amount)));
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