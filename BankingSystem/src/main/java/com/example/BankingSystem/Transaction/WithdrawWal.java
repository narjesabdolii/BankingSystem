package com.example.BankingSystem.Transaction;

import com.example.BankingSystem.Model.BankAccount;
import org.springframework.stereotype.Component;

@Component
public class WithdrawWal implements Transaction{

    @Override
    public void execute(BankAccount account, double amount) {
        account.withdraw(amount);
    }
}
