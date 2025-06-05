package fundamentos.adcbank.services;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

/**
 * @brief Command class for executing deposit transactions in the ADCBank application.
 */
public class DepositCommand implements TransactionCommand {

    /**
     * @brief Executes a deposit transaction by updating the account balance.
     * @param accountId The account ID to deposit into.
     * @param amount The amount to deposit.
     * @param targetAccountId Not used for deposit transactions.
     */
    @Override
    public void execute(String accountId, double amount, String targetAccountId) {
        try {
            MongoCollection<Document> accounts = DatabaseService.getInstance()
                    .getDatabase()
                    .getCollection("accounts");
            Document query = new Document("_id", accountId);
            Document account = accounts.find(query).first();
            if (account == null) {
                throw new RuntimeException("Account not found");
            }
            Object balanceValue = account.get("balance");
            double currentBalance = 0.0;
            if (balanceValue instanceof Integer) {
                currentBalance = ((Integer) balanceValue).doubleValue();
            } else if (balanceValue instanceof Double) {
                currentBalance = (Double) balanceValue;
            }
            double newBalance = currentBalance + amount;
            accounts.updateOne(query, new Document("$set", new Document("balance", newBalance)));

            // Record the transaction
            TransactionService.getInstance().recordTransaction(
                    accountId, "DEPOSIT", amount, null, "Deposit to account"
            );

        } catch (Exception e) {
            throw e;
        }
    }
}