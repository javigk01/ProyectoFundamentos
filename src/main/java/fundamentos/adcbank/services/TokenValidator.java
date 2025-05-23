package fundamentos.adcbank.services;

/**
 * @brief Validator class to check the validity of authentication tokens in the ADCBank application.
 */
public class TokenValidator extends TransactionValidator {

    /** @brief Authentication service instance for token validation. */
    private AuthenticationService authService;

    /**
     * @brief Constructor for TokenValidator.
     * @param authService The AuthenticationService instance to use for validation.
     */
    public TokenValidator(AuthenticationService authService) {
        this.authService = authService;
    }

    /**
     * @brief Validates the provided authentication token.
     * @param accountId The source account ID.
     * @param amount The transaction amount.
     * @param targetAccountId The target account ID (for transfers).
     * @param token The authentication token to validate.
     * @return True if the token is valid, false otherwise.
     */
    @Override
    public boolean validate(String accountId, double amount, String targetAccountId, String token) {
        if (!authService.validateToken(token)) {
            return false;
        }
        if (next != null) {
            return next.validate(accountId, amount, targetAccountId, token);
        }
        return true;
    }
}