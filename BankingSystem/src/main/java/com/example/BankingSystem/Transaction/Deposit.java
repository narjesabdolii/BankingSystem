package com.example.BankingSystem.Transaction;

import com.example.BankingSystem.Model.BankAccount;
import org.springframework.stereotype.Component;

@Component
public class Deposit implements Transaction{

    @Override
    public void execute(BankAccount account, double amount) {
        account.deposit(amount);
    }
}
