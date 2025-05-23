package fundamentos.adcbank.services;

public class TokenValidator extends TransactionValidator {
    private AuthenticationService authService;

    public TokenValidator(AuthenticationService authService) {
        this.authService = authService;
    }

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