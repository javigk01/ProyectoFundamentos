package fundamentos.adcbank.services;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

/**
 * @brief Command class for executing transfer transactions in the ADCBank application.
 */
public class TransferCommand implements TransactionCommand {

    /**
     * @brief Executes a transfer transaction between two accounts.
     * @param sourceAccountId The source account ID.
     * @param amount The amount to transfer.
     * @param targetAccountId The target account ID.
     */
    @Override
    public void execute(String sourceAccountId, double amount, String targetAccountId) {
        try {
            MongoCollection<Document> accounts = DatabaseService.getInstance()
                    .getDatabase()
                    .getCollection("accounts");
            Document sourceQuery = new Document("_id", sourceAccountId.toUpperCase());
            Document sourceAccount = accounts.find(sourceQuery).first();
            if (sourceAccount == null) {
                throw new RuntimeException("Source account not found");
            }
            Document targetQuery = new Document("_id", targetAccountId.toUpperCase());
            Document targetAccount = accounts.find(targetQuery).first();
            if (targetAccount == null) {
                throw new RuntimeException("Target account not found");
            }
            double sourceBalance = convertBalance(sourceAccount.get("balance"));
            double targetBalance = convertBalance(targetAccount.get("balance"));
            accounts.updateOne(sourceQuery,
                    new Document("$set", new Document("balance", sourceBalance - amount)));
            accounts.updateOne(targetQuery,
                    new Document("$set", new Document("balance", targetBalance + amount)));
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * @brief Converts a balance value to a double.
     * @param balanceValue The balance value to convert.
     * @return The balance as a double.
     * @throws IllegalArgumentException If the balance type is invalid.
     */
    private double convertBalance(Object balanceValue) {
        if (balanceValue instanceof Integer) {
            return ((Integer) balanceValue).doubleValue();
        } else if (balanceValue instanceof Double) {
            return (Double) balanceValue;
        }
        throw new IllegalArgumentException("Invalid balance type");
    }
}