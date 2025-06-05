package fundamentos.adcbank.services;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import java.util.Date;

/**
 * @brief Command class for executing withdrawal transactions in the ADCBank application.
 */
public class WithdrawCommand implements TransactionCommand {

    /**
     * @brief Executes a withdrawal transaction by updating the account balance.
     * @param accountId The account ID to withdraw from.
     * @param amount The amount to withdraw.
     * @param targetAccountId Not used for withdrawal transactions.
     */
    @Override
    public void execute(String accountId, double amount, String targetAccountId) {
        try {
            MongoCollection<Document> accounts = DatabaseService.getInstance()
                    .getDatabase()
                    .getCollection("accounts");
            Document query = new Document("_id", accountId.toUpperCase());
            Document account = accounts.find(query).first();
            if (account == null) {
                throw new RuntimeException("Account not found");
            }
            Object balanceValue = account.get("balance");
            double balance = convertBalance(balanceValue);
            if (balance < amount) {
                throw new RuntimeException("Insufficient funds");
            }
            accounts.updateOne(query,
                    new Document("$set", new Document("balance", balance - amount)));

            // Record the transaction
            TransactionService.getInstance().recordTransaction(
                    accountId.toUpperCase(), "WITHDRAW", amount, null, "Withdrawal from account"
            );

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