package com.example.BankingSystem;


import com.example.BankingSystem.Model.BankAccount;
import com.example.BankingSystem.Observer.TransactionLogger;
import com.example.BankingSystem.Service.BankService;
import com.example.BankingSystem.Transaction.Deposit;
import com.example.BankingSystem.Transaction.WithdrawWal;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import java.util.Scanner;

@SpringBootApplication
public class BankingSystemApplication implements CommandLineRunner {


	@Autowired
	private BankService bankService;

	@Autowired
	private Deposit deposit;

	@Autowired
	private WithdrawWal withdrawal;

	@Autowired
	private TransactionLogger transactionLogger;

	public static void main(String[] args) {
		SpringApplication.run(BankingSystemApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		bankService.registerObserver(transactionLogger);
		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.println("1. Create Account");
			System.out.println("2. Transfer Fund");
			System.out.println("3. Withdraw");
			System.out.println("4. Deposit");
			System.out.println("5. Check Balance");
			System.out.println("6. Exit");
			System.out.print("select a menu Item: ");
			int choice = scanner.nextInt();
			switch (choice) {
				case 1:
					System.out.print("Enter account holder name: ");
					String name = scanner.next();
					System.out.print("Enter initial balance: ");
					double initialBalance = scanner.nextDouble();
					BankAccount account = bankService.createAccount(name, initialBalance);
					System.out.println("Account created with account number: " + account.getAccountNumber());
					break;
				case 2:
					System.out.print("Enter your account number: ");
					long source = scanner.nextLong();
					BankAccount sourceAccount = bankService.findAccountInformationById(source);
					System.out.print("Enter destination account number: ");
					long accountNumber = scanner.nextLong();
					System.out.print("Enter amount to transfer: ");
					double depositAmount = scanner.nextDouble();
					bankService.doTransaction(sourceAccount ,accountNumber, depositAmount);
					System.out.println("Amount transfered.");
					break;
				case 3:
					System.out.print("Enter account number: ");
					long withdrawalAccountNumber = scanner.nextLong();
					System.out.print("Enter amount to withdraw: ");
					double withdrawalAmount = scanner.nextDouble();
					bankService.withdraw(withdrawalAccountNumber, withdrawalAmount);
					System.out.println("Amount withdrawn.");
					break;
				case 4:
					System.out.print("Enter  account number: ");
					long accountNumberDeposit = scanner.nextLong();
					System.out.print("Enter amount to deposit: ");
					double transferAmount = scanner.nextDouble();
					bankService.deposit(accountNumberDeposit ,transferAmount);
					System.out.println("Amount transferred.");
					break;
				case 5:
					System.out.print("Enter account number: ");
					long balanceAccountNumber = scanner.nextLong();
					BankAccount balanceAccount = bankService.findAccountInformationById(balanceAccountNumber);
					System.out.println("Current balance: " + balanceAccount.getBalance());
					break;
				case 6:
					System.out.println("Exiting...");
					return;
				default:
					System.out.println("Invalid choice. Please try again.");
			}
		}
	}
}
