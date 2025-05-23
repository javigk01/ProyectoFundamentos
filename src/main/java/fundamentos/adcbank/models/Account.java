package fundamentos.adcbank.models;

/**
 * @brief Model class representing a bank account in the ADCBank application.
 */
public class Account {

    /** @brief The unique identifier of the account. */
    private String id;

    /** @brief The user ID associated with the account. */
    private String userId;

    /** @brief The type of the account (e.g., checking). */
    private String type;

    /** @brief The current balance of the account. */
    private double balance;

    /**
     * @brief Gets the account ID.
     * @return The account ID.
     */
    public String getId() {
        return id;
    }

    /**
     * @brief Sets the account ID.
     * @param id The account ID to set.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @brief Gets the user ID associated with the account.
     * @return The user ID.
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @brief Sets the user ID for the account.
     * @param userId The user ID to set.
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @brief Gets the account type.
     * @return The account type.
     */
    public String getType() {
        return type;
    }

    /**
     * @brief Sets the account type.
     * @param type The account type to set.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @brief Gets the account balance.
     * @return The current balance.
     */
    public double getBalance() {
        return balance;
    }

    /**
     * @brief Sets the account balance.
     * @param balance The balance to set.
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }
}