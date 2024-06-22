package com.example.BankingSystem.Observer;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@Component
public class TransactionLogger implements Observer {
    private static final String LOG_FILE_PATH = "transactions.log";
    private List<TransactionLogItem> items = new ArrayList<>();
    private PrintWriter writer;

    @PostConstruct
    public void init() {
        try {
            writer = new PrintWriter(new FileWriter(LOG_FILE_PATH, true), true);
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize logger", e);
        }
    }

    @Override
    public void log(TransactionLogItem item) {
        items.add(item);
        String logMessage = "Transaction logged: " + item.getTransActionType() + " of " + item.getAmount() + " on account " +
                item.getAccount().getAccountNumber() + " with holder name : " + item.getAccount().getAccountHolderName();
        writer.println(logMessage);
        System.out.println(logMessage);
    }

    public List<TransactionLogItem> getEvents() {
        return items;
    }

}
