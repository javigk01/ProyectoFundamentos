package fundamentos.adcbank.services;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class TransferCommand implements TransactionCommand {
    @Override
    public void execute(String sourceAccountId, double amount, String targetAccountId) {
        try {
            System.out.println("Transferring " + amount + " from " + sourceAccountId + " to " + targetAccountId);

            MongoCollection<Document> accounts = DatabaseService.getInstance()
                    .getDatabase()
                    .getCollection("accounts");

            // Source account operations
            Document sourceQuery = new Document("_id", sourceAccountId.toUpperCase());
            Document sourceAccount = accounts.find(sourceQuery).first();

            if (sourceAccount == null) {
                throw new RuntimeException("Source account not found");
            }

            // Target account operations
            Document targetQuery = new Document("_id", targetAccountId.toUpperCase());
            Document targetAccount = accounts.find(targetQuery).first();

            if (targetAccount == null) {
                throw new RuntimeException("Target account not found");
            }

            // Convert balances
            double sourceBalance = convertBalance(sourceAccount.get("balance"));
            double targetBalance = convertBalance(targetAccount.get("balance"));

            System.out.println("Source balance before: " + sourceBalance);
            System.out.println("Target balance before: " + targetBalance);

            // Update both accounts in sequence
            accounts.updateOne(sourceQuery,
                    new Document("$set", new Document("balance", sourceBalance - amount)));

            accounts.updateOne(targetQuery,
                    new Document("$set", new Document("balance", targetBalance + amount)));

            System.out.println("Transfer successful");
            System.out.println("Source balance after: " + (sourceBalance - amount));
            System.out.println("Target balance after: " + (targetBalance + amount));
        } catch (Exception e) {
            System.out.println("Transfer failed: " + e.getMessage());
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