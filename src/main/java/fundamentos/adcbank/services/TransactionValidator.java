package fundamentos.adcbank.services;

/**
 * @brief Abstract class for validating transactions in the ADCBank application.
 */
public abstract class TransactionValidator {

    /** @brief The next validator in the chain of responsibility. */
    protected TransactionValidator next;

    /**
     * @brief Sets the next validator in the chain.
     * @param next The next TransactionValidator to set.
     */
    public void setNext(TransactionValidator next) {
        this.next = next;
    }

    /**
     * @brief Validates a transaction based on specific criteria.
     * @param accountId The source account ID.
     * @param amount The transaction amount.
     * @param targetAccountId The target account ID (for transfers).
     * @param token The authentication token.
     * @return True if the transaction is valid, false otherwise.
     */
    public abstract boolean validate(String accountId, double amount, String targetAccountId, String token);
}