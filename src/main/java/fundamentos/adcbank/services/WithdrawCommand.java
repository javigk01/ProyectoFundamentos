package fundamentos.adcbank.services;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import java.util.Date;

public class WithdrawCommand implements TransactionCommand {
    @Override
    public void execute(String accountId, double amount, String targetAccountId) {
        MongoCollection<Document> accounts = DatabaseService.getInstance().getDatabase().getCollection("accounts");
        Document query = new Document("_id", accountId);
        Document accountDoc = accounts.find(query).first();
        if (accountDoc != null) {
            double currentBalance = accountDoc.getDouble("balance");
            if (currentBalance >= amount) {
                double newBalance = currentBalance - amount;
                accounts.updateOne(query, new Document("$set", new Document("balance", newBalance)));
                MongoCollection<Document> transactions = DatabaseService.getInstance().getDatabase().getCollection("transactions");
                Document transactionDoc = new Document("accountId", accountId)
                        .append("type", "withdraw")
                        .append("amount", amount)
                        .append("timestamp", new Date());
                transactions.insertOne(transactionDoc);
            } else {
                throw new RuntimeException("Insufficient balance");
            }
        }
    }
}