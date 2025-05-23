package fundamentos.adcbank.services;

import fundamentos.adcbank.models.Account;

public class AccountFactory {
    public Account createAccount(String type, String userId) {
        Account account = new Account();
        account.setType(type);
        account.setUserId(userId);
        account.setBalance(0.0);
        return account;
    }
}