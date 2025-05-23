package fundamentos.adcbank.services;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

/**
 * @brief Validator class to check sufficient balance for transactions in the ADCBank application.
 */
public class BalanceValidator extends TransactionValidator {

    /**
     * @brief Validates if the account has sufficient balance for a transaction.
     * @param accountId The source account ID.
     * @param amount The transaction amount.
     * @param targetAccountId The target account ID (for transfers).
     * @param token The authentication token.
     * @return True if the account has sufficient balance, false otherwise.
     */
    @Override
    public boolean validate(String accountId, double amount, String targetAccountId, String token) {
        try {
            MongoCollection<Document> accounts = DatabaseService.getInstance()
                    .getDatabase()
                    .getCollection("accounts");
            Document account = accounts.find(new Document("_id", accountId.toUpperCase())).first();
            if (account == null) {
                return false;
            }
            Object balanceValue = account.get("balance");
            double balance;
            if (balanceValue instanceof Integer) {
                balance = ((Integer) balanceValue).doubleValue();
            } else if (balanceValue instanceof Double) {
                balance = (Double) balanceValue;
            } else if (balanceValue instanceof Long) {
                balance = ((Long) balanceValue).doubleValue();
            } else {
                return false;
            }
            if (balance < amount) {
                return false;
            }
            return next == null || next.validate(accountId, amount, targetAccountId, token);
        } catch (Exception e) {
            return false;
        }
    }
}