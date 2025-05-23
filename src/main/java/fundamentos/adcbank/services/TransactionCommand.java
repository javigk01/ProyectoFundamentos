package fundamentos.adcbank.services;

/**
 * @brief Interface for executing transaction commands in the ADCBank application.
 */
public interface TransactionCommand {

    /**
     * @brief Executes a transaction with the specified parameters.
     * @param accountId The source account ID.
     * @param amount The transaction amount.
     * @param targetAccountId The target account ID (for transfers).
     */
    void execute(String accountId, double amount, String targetAccountId);
}