package com.demo.banking.app.team1.service;

import com.demo.banking.app.team1.model.Account;
import com.demo.banking.app.team1.model.Transaction;

import java.util.List;
import java.util.Optional;

public interface AccountService {

    Account createAccount(String iban, String ownerName, double balance);

    Optional<Account> getAccountById(long id);

    List<Account> getAllAccounts();

    List<Transaction> getTransactionsForAccount(long accountId);

    Account updateBalance(long accountId, double newBalance);

    void addTransaction(long accountId, Transaction transaction);
}