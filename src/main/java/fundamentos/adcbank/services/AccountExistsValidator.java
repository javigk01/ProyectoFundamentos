package fundamentos.adcbank.services;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

/**
 * @brief Validator class to check if a target account exists for transactions.
 */
public class AccountExistsValidator extends TransactionValidator {

    /**
     * @brief Validates if the target account exists for a transaction.
     * @param accountId The source account ID.
     * @param amount The transaction amount.
     * @param targetAccountId The target account ID.
     * @param token The authentication token.
     * @return True if the target account exists, false otherwise.
     */
    @Override
    public boolean validate(String accountId, double amount, String targetAccountId, String token) {
        if (targetAccountId == null) {
            return true;
        }
        MongoCollection<Document> accounts = DatabaseService.getInstance().getDatabase().getCollection("accounts");
        Document query = new Document("_id", targetAccountId);
        Document accountDoc = accounts.find(query).first();
        if (accountDoc == null) {
            return false;
        }
        if (next != null) {
            return next.validate(accountId, amount, targetAccountId, token);
        }
        return true;
    }
}