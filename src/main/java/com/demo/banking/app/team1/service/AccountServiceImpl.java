package com.demo.banking.app.team1.service;

import com.demo.banking.app.team1.model.Account;
import com.demo.banking.app.team1.model.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

    private final Map<Long, Account> accounts = new ConcurrentHashMap<>();
    private final Map<Long, List<Transaction>> transactionsByAccount = new ConcurrentHashMap<>();

    @Override
    public Account createAccount(String iban, String ownerName, double balance) {
        Account account = new Account(iban, ownerName, balance);
        accounts.put(account.getId(), account);
        transactionsByAccount.put(account.getId(), new CopyOnWriteArrayList<>());
        log.info("Account created: id={}, iban={}, ownerName={}, initialBalance={}",
                account.getId(), account.getIban(), account.getOwnerName(), account.getBalance());
        return account;
    }

    @Override
    public Optional<Account> getAccountById(long id) {
        Optional<Account> account = Optional.ofNullable(accounts.get(id));
        if (account.isEmpty()) {
            log.warn("Account lookup failed: no account found with id={}", id);
        } else {
            log.info("Account lookup succeeded: id={}", id);
        }
        return account;
    }


    @Override
    public List<Account> getAllAccounts() {
        List<Account> all = List.copyOf(accounts.values());
        log.info("Retrieved all accounts: count= {}", all.size());
        return all;
    }

    @Override
    public List<Transaction> getTransactionsForAccount(long accountId) {
        requireAccount(accountId);
        List<Transaction> transactions = List.copyOf(transactionsByAccount.get(accountId));
        log.info("Retrieved transactions for account {}: count= {}", accountId, transactions.size());
        return transactions;
    }

    @Override
    public Account updateBalance(long accountId, double newBalance) {
        if (newBalance < 0) {
            log.warn("Balance update failed for account {}: negative balance requested ({})",
                    accountId, newBalance);
            throw new IllegalArgumentException("Balance cannot be negative");
        }
        Account account = requireAccount(accountId);
        account.setBalance(newBalance);
        double oldBalance = account.getBalance();
        log.info("Balance updated for account {}: {} -> {}", accountId, oldBalance, newBalance);
        return account;
    }

    @Override
    public void addTransaction(long accountId, Transaction transaction) {
        if (transaction == null) {
            log.error("Failed to add transaction for account {}: transaction was null", accountId);
            throw new IllegalArgumentException("Transaction cannot be null");
        }
        requireAccount(accountId);
        transactionsByAccount.get(accountId).add(transaction);
        log.info("Transaction added for account {}: type={}, amount={}",
                accountId, transaction.getType(), transaction.getAmount());
    }

    private Account requireAccount(long accountId) {
        Account account = accounts.get(accountId);
        if (account == null) {
            log.warn("Account not found with id = {}", accountId);
            throw new NoSuchElementException("Account not found with id: " + accountId);
        }
        return account;
    }
}
