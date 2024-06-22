package com.example.BankingSystem.Transaction;

import com.example.BankingSystem.Model.BankAccount;

public interface Transaction {
    void execute(BankAccount account, double amount);
}

