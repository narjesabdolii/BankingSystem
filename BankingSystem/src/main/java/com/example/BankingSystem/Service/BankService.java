package com.example.BankingSystem.Service;

import com.example.BankingSystem.Model.BankAccount;
import com.example.BankingSystem.Observer.Observer;
import com.example.BankingSystem.Observer.Subject;
import com.example.BankingSystem.Observer.TransactionLogItem;
import com.example.BankingSystem.Repositoty.BankAccountRepository;
import com.example.BankingSystem.Transaction.Deposit;
import com.example.BankingSystem.Transaction.TransferFund;
import com.example.BankingSystem.Transaction.WithdrawWal;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class BankService implements Subject {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private Deposit deposit;

    @Autowired
    private WithdrawWal withdrawal;

    @Autowired
    private TransferFund transferFund;

    @Getter
    @Setter
    private List<Observer> observers= new ArrayList<>();

    private final ExecutorService executorService = Executors.newFixedThreadPool(10);


    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(TransactionLogItem item) {
        for (Observer observer : observers) {
            observer.log(item);
        }
    }
    public BankAccount createAccount(String accountHolderName, double initialBalance) {
        BankAccount account = new BankAccount(accountHolderName, initialBalance);
        Date date = new Date();
        account.setCreatedDate(date.toString());
        return bankAccountRepository.saveAndFlush(account);
    }

    @Transactional
    public synchronized void doTransaction(BankAccount source , Long accountNumber, double amount) {
        executorService.submit(() -> {
            synchronized (this) {
                transferFund.setDestinationAccountNumber(accountNumber);
                transferFund.execute(source, amount);
                bankAccountRepository.saveAndFlush(source);
            }
        });

    }

    @Transactional
    public synchronized void deposit(Long accountNumber, double amount) {
        BankAccount account = bankAccountRepository.findBankAccountByAccountNumber(accountNumber);
        deposit.execute(account, amount);
        bankAccountRepository.saveAndFlush(account);
    }

    @Transactional
    public synchronized void withdraw(Long accountNumber, double amount) {
        BankAccount account = bankAccountRepository.findBankAccountByAccountNumber(accountNumber);
        withdrawal.execute(account, amount);
        bankAccountRepository.saveAndFlush(account);
    }




    public List<BankAccount> getAllAccounts() {
        return bankAccountRepository.findAll();
    }

    public BankAccount findAccountInformationById(Long accountNumber) {
        return bankAccountRepository.findBankAccountByAccountNumber(accountNumber);
    }


    public List<BankAccount> findAccountByHolderName(String name) {
        return bankAccountRepository.findAllByAccountHolderName(name);
    }




}
