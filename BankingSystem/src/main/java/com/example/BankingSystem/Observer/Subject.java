package com.example.BankingSystem.Observer;

public interface Subject {

    void registerObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers(TransactionLogItem item);
}
