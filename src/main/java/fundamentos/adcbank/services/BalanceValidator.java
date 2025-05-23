package fundamentos.adcbank.services;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class BalanceValidator extends TransactionValidator {
    @Override
    public boolean validate(String accountId, double amount, String targetAccountId, String token) {
        MongoCollection<Document> accounts = DatabaseService.getInstance().getDatabase().getCollection("accounts");
        Document query = new Document("_id", accountId);
        Document accountDoc = accounts.find(query).first();
        if (accountDoc != null) {
            double balance = accountDoc.getDouble("balance");
            if (balance < amount) {
                return false;
            }
        } else {
            return false;
        }
        if (next != null) {
            return next.validate(accountId, amount, targetAccountId, token);
        }
        return true;
    }
}