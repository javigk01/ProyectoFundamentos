package fundamentos.adcbank.services;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import java.util.Date;

public class TransferCommand implements TransactionCommand {
    @Override
    public void execute(String accountId, double amount, String targetAccountId) {
        if (targetAccountId == null) {
            throw new RuntimeException("Target account ID is required for transfer");
        }
        MongoCollection<Document> accounts = DatabaseService.getInstance().getDatabase().getCollection("accounts");
        Document targetQuery = new Document("_id", targetAccountId);
        Document targetAccountDoc = accounts.find(targetQuery).first();
        if (targetAccountDoc == null) {
            throw new RuntimeException("Target account does not exist");
        }
        Document sourceQuery = new Document("_id", accountId);
        Document sourceAccountDoc = accounts.find(sourceQuery).first();
        if (sourceAccountDoc != null) {
            double currentBalance = sourceAccountDoc.getDouble("balance");
            if (currentBalance >= amount) {
                double newSourceBalance = currentBalance - amount;
                accounts.updateOne(sourceQuery, new Document("$set", new Document("balance", newSourceBalance)));
                double targetBalance = targetAccountDoc.getDouble("balance");
                double newTargetBalance = targetBalance + amount;
                accounts.updateOne(targetQuery, new Document("$set", new Document("balance", newTargetBalance)));
                MongoCollection<Document> transactions = DatabaseService.getInstance().getDatabase().getCollection("transactions");
                Document transactionDoc = new Document("accountId", accountId)
                        .append("type", "transfer")
                        .append("amount", amount)
                        .append("targetAccountId", targetAccountId)
                        .append("timestamp", new Date());
                transactions.insertOne(transactionDoc);
            } else {
                throw new RuntimeException("Insufficient balance");
            }
        }
    }
}