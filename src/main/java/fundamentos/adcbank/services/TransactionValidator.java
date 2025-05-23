package fundamentos.adcbank.services;

public abstract class TransactionValidator {
    protected TransactionValidator next;

    public void setNext(TransactionValidator next) {
        this.next = next;
    }

    public abstract boolean validate(String accountId, double amount, String targetAccountId, String token);
}