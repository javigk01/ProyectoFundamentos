package fundamentos.adcbank.services;

public interface TransactionCommand {
    void execute(String accountId, double amount, String targetAccountId);
}