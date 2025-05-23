package fundamentos.adcbank.services;

import fundamentos.adcbank.models.Account;

public class AccountFactory {
    public Account createAccount(String type, String userId) {
        Account account = new Account();
        account.setId(generateAccountId());
        account.setType(type);
        account.setUserId(userId);
        account.setBalance(0.0);
        return account;
    }

    private String generateAccountId() {
        return "ACCT-" + System.currentTimeMillis();
    }
}