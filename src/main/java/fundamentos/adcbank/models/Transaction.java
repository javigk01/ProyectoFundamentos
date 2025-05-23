package fundamentos.adcbank.models;

import java.util.Date;

/**
 * @brief Model class representing a transaction in the ADCBank application.
 */
public class Transaction {

    /** @brief The unique identifier of the transaction. */
    private String id;

    /** @brief The account ID associated with the transaction. */
    private String accountId;

    /** @brief The type of the transaction (e.g., deposit, withdrawal). */
    private String type;

    /** @brief The amount involved in the transaction. */
    private double amount;

    /** @brief The timestamp of the transaction. */
    private Date timestamp;

    /** @brief The target account ID for transfer transactions. */
    private String targetAccountId;

    /**
     * @brief Gets the transaction ID.
     * @return The transaction ID.
     */
    public String getId() {
        return id;
    }

    /**
     * @brief Sets the transaction ID.
     * @param id The transaction ID to set.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @brief Gets the account ID associated with the transaction.
     * @return The account ID.
     */
    public String getAccountId() {
        return accountId;
    }

    /**
     * @brief Sets the account ID for the transaction.
     * @param accountId The account ID to set.
     */
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    /**
     * @brief Gets the transaction type.
     * @return The transaction type.
     */
    public String getType() {
        return type;
    }

    /**
     * @brief Sets the transaction type.
     * @param type The transaction type to set.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @brief Gets the transaction amount.
     * @return The transaction amount.
     */
    public double getAmount() {
        return amount;
    }

    /**
     * @brief Sets the transaction amount.
     * @param amount The amount to set.
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * @brief Gets the transaction timestamp.
     * @return The transaction timestamp.
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * @brief Sets the transaction timestamp.
     * @param timestamp The timestamp to set.
     */
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * @brief Gets the target account ID for transfer transactions.
     * @return The target account ID.
     */
    public String getTargetAccountId() {
        return targetAccountId;
    }

    /**
     * @brief Sets the target account ID for transfer transactions.
     * @param targetAccountId The target account ID to set.
     */
    public void setTargetAccountId(String targetAccountId) {
        this.targetAccountId = targetAccountId;
    }
}