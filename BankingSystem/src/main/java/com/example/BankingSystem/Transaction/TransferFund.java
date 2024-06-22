package com.example.BankingSystem.Transaction;

import com.example.BankingSystem.Model.BankAccount;
import com.example.BankingSystem.Repositoty.BankAccountRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TransferFund implements Transaction{

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Getter
    @Setter
    private Long destinationAccountNumber;

    @Override
    public void execute(BankAccount sourceAccount, double amount) {
        BankAccount destination = bankAccountRepository.findBankAccountByAccountNumber(destinationAccountNumber);
        sourceAccount.withdraw(amount);
        destination.deposit(amount);
        Date date = new Date();
        destination.setModifiedDate(date.toString());
        sourceAccount.setModifiedDate(date.toString());
        bankAccountRepository.saveAndFlush(destination);
    }
}
