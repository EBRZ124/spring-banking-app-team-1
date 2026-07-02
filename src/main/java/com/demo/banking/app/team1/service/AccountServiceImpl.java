package com.demo.banking.app.team1.service;

import com.demo.banking.app.team1.model.Account;
import com.demo.banking.app.team1.model.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class AccountServiceImpl implements AccountService {

    private final Map<Long, Account> accounts = new ConcurrentHashMap<>();
    private final Map<Long, List<Transaction>> transactionsByAccount = new ConcurrentHashMap<>();

    @Override
    public Account createAccount(String iban, String ownerName, double balance) {
        Account account = new Account(iban, ownerName, balance);
        accounts.put(account.getId(), account);
        transactionsByAccount.put(account.getId(), new CopyOnWriteArrayList<>());
        return account;
    }

    @Override
    public Optional<Account> getAccountById(long id) {
        return Optional.ofNullable(accounts.get(id));
    }

    @Override
    public List<Account> getAllAccounts() {
        return List.copyOf(accounts.values());
    }

    @Override
    public List<Transaction> getTransactionsForAccount(long accountId) {
        requireAccount(accountId);
        return List.copyOf(transactionsByAccount.get(accountId));
    }

    @Override
    public Account updateBalance(long accountId, double newBalance) {
        if (newBalance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }
        Account account = requireAccount(accountId);
        account.setBalance(newBalance);
        return account;
    }

    @Override
    public void addTransaction(long accountId, Transaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction cannot be null");
        }
        requireAccount(accountId);
        transactionsByAccount.get(accountId).add(transaction);
    }

    private Account requireAccount(long accountId) {
        Account account = accounts.get(accountId);
        if (account == null) {
            throw new NoSuchElementException("Account not found with id: " + accountId);
        }
        return account;
    }
}
