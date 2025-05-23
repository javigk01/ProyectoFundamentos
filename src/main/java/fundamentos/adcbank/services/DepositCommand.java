package fundamentos.adcbank.services;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class DepositCommand implements TransactionCommand {
    @Override
    public void execute(String accountId, double amount, String targetAccountId) {
        try {
            MongoCollection<Document> accounts = DatabaseService.getInstance()
                    .getDatabase()
                    .getCollection("accounts");

            Document query = new Document("_id", accountId);
            Document account = accounts.find(query).first();

            if (account == null) {
                System.out.println("Account not found: " + accountId);
                throw new RuntimeException("Account not found");
            }

            Object balanceValue = account.get("balance");
            double currentBalance = 0.0;

            if (balanceValue instanceof Integer) {
                currentBalance = ((Integer) balanceValue).doubleValue();
            } else if (balanceValue instanceof Double) {
                currentBalance = (Double) balanceValue;
            }

            System.out.println("Current balance: " + currentBalance); // Debug log

            double newBalance = currentBalance + amount;
            accounts.updateOne(query, new Document("$set", new Document("balance", newBalance)));

            System.out.println("New balance: " + newBalance); // Debug log
        } catch (Exception e) {
            System.out.println("DepositCommand error: " + e.getMessage());
            throw e;
        }
    }
}