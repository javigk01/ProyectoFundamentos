package fundamentos.adcbank.services;

import fundamentos.adcbank.models.Account;

/**
 * @brief Factory class for creating bank accounts in the ADCBank application.
 */
public class AccountFactory {

    /**
     * @brief Creates a new account with the specified type and user ID.
     * @param type The type of the account (e.g., checking).
     * @param userId The ID of the user associated with the account.
     * @return The created Account object.
     */
    public Account createAccount(String type, String userId) {
        Account account = new Account();
        account.setId(generateAccountId());
        account.setType(type);
        account.setUserId(userId);
        account.setBalance(0.0);
        return account;
    }

    /**
     * @brief Generates a unique account ID based on the current timestamp.
     * @return The generated account ID.
     */
    private String generateAccountId() {
        return "ACCT-" + System.currentTimeMillis();
    }
}