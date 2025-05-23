package fundamentos.adcbank.services;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class BalanceValidator extends TransactionValidator {
    @Override
    public boolean validate(String accountId, double amount, String targetAccountId, String token) {
        MongoCollection<Document> accounts = DatabaseService.getInstance().getDatabase().getCollection("accounts");
        Document account = accounts.find(new Document("_id", accountId)).first();
        if (account == null) {
            return false;
        }

        // Handle Integer/Double balance
        Object balanceValue = account.get("balance");
        double balance;
        if (balanceValue instanceof Integer) {
            balance = ((Integer) balanceValue).doubleValue();
        } else if (balanceValue instanceof Double) {
            balance = (Double) balanceValue;
        } else {
            return false; // Invalid type
        }

        if (balance < amount) {
            return false;
        }

        return next == null || next.validate(accountId, amount, targetAccountId, token);
    }
}