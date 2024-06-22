package com.example.BankingSystem.Repositoty;


import com.example.BankingSystem.Model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

     BankAccount findBankAccountByAccountNumber(Long accountNumber);

     List<BankAccount> findAllByAccountHolderName(String name);


}

