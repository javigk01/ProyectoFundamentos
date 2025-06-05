package fundamentos.adcbank.services;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

/**
 * @brief Service class for managing transaction records in the ADCBank application.
 */
public class TransactionService {

    /** @brief Singleton instance of the TransactionService. */
    private static TransactionService instance;

    /**
     * @brief Private constructor to enforce singleton pattern.
     */
    private TransactionService() {}

    /**
     * @brief Gets the singleton instance of the TransactionService.
     * @return The TransactionService instance.
     */
    public static TransactionService getInstance() {
        if (instance == null) {
            instance = new TransactionService();
        }
        return instance;
    }

    /**
     * @brief Records a transaction in the database.
     * @param accountId The account ID involved in the transaction.
     * @param type The type of transaction (DEPOSIT, WITHDRAW, TRANSFER).
     * @param amount The transaction amount.
     * @param targetAccountId The target account ID (for transfers, null otherwise).
     * @param description Additional description for the transaction.
     */
    public void recordTransaction(String accountId, String type, double amount, 
                                 String targetAccountId, String description) {
        try {
            MongoCollection<Document> transactions = DatabaseService.getInstance()
                    .getDatabase()
                    .getCollection("transactions");

            String transactionId = "TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
            
            Document transaction = new Document()
                    .append("_id", transactionId)
                    .append("accountId", accountId)
                    .append("type", type)
                    .append("amount", amount)
                    .append("timestamp", new Date())
                    .append("description", description);

            if (targetAccountId != null) {
                transaction.append("targetAccountId", targetAccountId);
            }

            transactions.insertOne(transaction);
        } catch (Exception e) {
            System.err.println("Error recording transaction: " + e.getMessage());
        }
    }

    /**
     * @brief Retrieves all transactions for a specific account, including transfers.
     * @param accountId The account ID to get transactions for.
     * @return List of transaction documents.
     */
    public List<Document> getTransactionHistory(String accountId) {
        try {
            MongoCollection<Document> transactions = DatabaseService.getInstance()
                    .getDatabase()
                    .getCollection("transactions");

            List<Document> transactionList = new ArrayList<>();
            
            // Get all transactions where this account is involved
            // This includes:
            // 1. Transactions where this account is the source (accountId field)
            // 2. Transfers where this account is the target (targetAccountId field)
            Document query = new Document("$or", List.of(
                new Document("accountId", accountId),
                new Document("targetAccountId", accountId)
            ));
            
            transactions.find(query)
                    .sort(new Document("timestamp", -1))
                    .into(transactionList);

            return transactionList;
        } catch (Exception e) {
            System.err.println("Error retrieving transaction history: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}