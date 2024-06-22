package com.example.BankingSystem.Observer;

import com.example.BankingSystem.Model.BankAccount;


public class TransactionLogItem {
    private final BankAccount account;
    private final double amount;
    private final String transActionType;

    public TransactionLogItem(BankAccount account, double amount, String transActionType) {
        this.account = account;
        this.amount = amount;
        this.transActionType = transActionType;
    }

    public BankAccount getAccount() {
        return account;
    }

    public double getAmount() {
        return amount;
    }

    public String getTransActionType() {
        return transActionType;
    }

}
