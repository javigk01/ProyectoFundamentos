package fundamentos.adcbank.services;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class AccountExistsValidator extends TransactionValidator {
    @Override
    public boolean validate(String accountId, double amount, String targetAccountId, String token) {
        if (targetAccountId == null) {
            return true; // Not applicable for non-transfers
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