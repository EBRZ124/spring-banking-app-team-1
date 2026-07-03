package com.demo.banking.app.team1.service;

import com.demo.banking.app.team1.dto.TransferRequestDto;
import com.demo.banking.app.team1.dto.TransferResponseDto;
import com.demo.banking.app.team1.model.Account;
import com.demo.banking.app.team1.model.Transaction;
import com.demo.banking.app.team1.model.Type;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Slf4j
@Service
public class TransferServiceImpl implements TransferService {
    private final AccountService accountService;

    public TransferServiceImpl(AccountService accountService){
        this.accountService = accountService;
    }

    @Override
    public synchronized TransferResponseDto transfer(TransferRequestDto request) {

        log.info("Transfer requested: fromAccountId={}, toAccountId={}, amount={}", request.getFromAccountId(),
                request.getToAccountId(), request.getAmount());

        if (request.getFromAccountId().equals(request.getToAccountId())) {
            log.warn("Transfer rejected: fromAccountId and toAccountId are the same ({})", request.getFromAccountId());
            throw new IllegalArgumentException("Cannot transfer to the same account.");
        }

        Account fromAccount = accountService.getAccountById(request.getFromAccountId())
                .orElseThrow(() -> {
                    log.error("Transfer failed: source account not found, id={}", request.getFromAccountId());
                    return new NoSuchElementException(
                            "Account not found with id: " + request.getFromAccountId());
                });

        Account toAccount = accountService.getAccountById(request.getToAccountId())
                .orElseThrow(() -> {
                    log.error("Transfer failed: destination account not found, id={}", request.getToAccountId());
                    return new NoSuchElementException(
                            "Account not found with id: " + request.getToAccountId());
                });

        double amount = request.getAmount();

        if (fromAccount.getBalance() < amount) {
            log.warn("Transfer rejected: insufficient funds in account {} (balance={}, requested={})",
                    fromAccount.getId(), fromAccount.getBalance(), amount);
            throw new IllegalArgumentException("Insufficient funds in the account " + fromAccount.getId());
        }

        accountService.updateBalance(fromAccount.getId(), fromAccount.getBalance() - amount);
        accountService.updateBalance(toAccount.getId(), toAccount.getBalance() + amount);

        log.info("Balances updated: account {} new balance={}, account {} new balance={}",
                fromAccount.getId(), fromAccount.getBalance(), toAccount.getId(), toAccount.getBalance());

        String note = request.getNote();

        Transaction withdrawl = new Transaction(fromAccount, Type.WITHDRAWAL, note, amount);
        Transaction deposit = new Transaction(toAccount, Type.DEPOSIT, note, amount);

        accountService.addTransaction(fromAccount.getId(), withdrawl);
        accountService.addTransaction(toAccount.getId(), deposit);

        log.info("Transfer completed successfully: {} moved from account {} to account {}",
                amount, fromAccount.getId(), toAccount.getId());

        return new TransferResponseDto(
                fromAccount.getId(),
                toAccount.getId(),
                amount,
                fromAccount.getBalance(),
                toAccount.getBalance(),
                LocalDateTime.now()
        );
    }
}
