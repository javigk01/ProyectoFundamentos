package fundamentos.adcbank.services;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class DepositCommand implements TransactionCommand {
    @Override
    public void execute(String accountId, double amount, String targetAccountId) {
        MongoCollection<Document> accounts = DatabaseService.getInstance().getDatabase().getCollection("accounts");
        Document query = new Document("_id", accountId);
        Document account = accounts.find(query).first();
        if (account != null) {
            Object balanceValue = account.get("balance");
            double balance = convertBalance(balanceValue);
            Document update = new Document("$set", new Document("balance", balance + amount));
            accounts.updateOne(query, update);
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