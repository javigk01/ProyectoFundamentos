package fundamentos.adcbank.services;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import java.util.UUID;

/**
 * @brief Command class for executing transfer transactions in the ADCBank application.
 */
public class TransferCommand implements TransactionCommand {

    /**
     * @brief Executes a transfer transaction between two accounts and sends email notifications.
     * @param sourceAccountId The source account ID.
     * @param amount The amount to transfer.
     * @param targetAccountId The target account ID.
     */
    @Override
    public void execute(String sourceAccountId, double amount, String targetAccountId) {
        try {
            MongoCollection<Document> accounts = DatabaseService.getInstance()
                    .getDatabase()
                    .getCollection("accounts");

            Document sourceQuery = new Document("_id", sourceAccountId.toUpperCase());
            Document sourceAccount = accounts.find(sourceQuery).first();
            if (sourceAccount == null) {
                throw new RuntimeException("Source account not found");
            }

            Document targetQuery = new Document("_id", targetAccountId.toUpperCase());
            Document targetAccount = accounts.find(targetQuery).first();
            if (targetAccount == null) {
                throw new RuntimeException("Target account not found");
            }

            double sourceBalance = convertBalance(sourceAccount.get("balance"));
            double targetBalance = convertBalance(targetAccount.get("balance"));

            // Update balances
            accounts.updateOne(sourceQuery,
                    new Document("$set", new Document("balance", sourceBalance - amount)));
            accounts.updateOne(targetQuery,
                    new Document("$set", new Document("balance", targetBalance + amount)));

            // Generate transaction ID for email notifications
            String transactionId = "TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

            // Send email notifications
            sendEmailNotifications(sourceAccountId, targetAccountId, amount, transactionId);

        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * @brief Sends email notifications to both sender and recipient.
     * @param sourceAccountId The source account ID.
     * @param targetAccountId The target account ID.
     * @param amount The transfer amount.
     * @param transactionId The transaction ID.
     */
    private void sendEmailNotifications(String sourceAccountId, String targetAccountId,
                                        double amount, String transactionId) {
        try {
            AuthenticationService authService = AuthenticationService.getInstance();
            EmailService emailService = EmailService.getInstance();

            // Get sender information
            Document senderUser = authService.getUserByAccountId(sourceAccountId);
            if (senderUser != null) {
                String senderEmail = senderUser.getString("email");
                String senderName = senderUser.getString("username");

                if (senderEmail != null && !senderEmail.isEmpty()) {
                    // Send confirmation email to sender
                    emailService.sendTransferConfirmationEmail(
                            senderEmail, senderName, targetAccountId, amount, transactionId
                    );
                }
            }

            // Get recipient information
            Document recipientUser = authService.getUserByAccountId(targetAccountId);
            if (recipientUser != null) {
                String recipientEmail = recipientUser.getString("email");
                String recipientName = recipientUser.getString("username");

                if (recipientEmail != null && !recipientEmail.isEmpty()) {
                    // Send notification email to recipient
                    emailService.sendTransferReceivedEmail(
                            recipientEmail, recipientName, sourceAccountId, amount, transactionId
                    );
                }
            }

        } catch (Exception e) {
            // Don't fail the transaction if email sending fails
            System.err.println("Warning: Failed to send email notifications: " + e.getMessage());
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