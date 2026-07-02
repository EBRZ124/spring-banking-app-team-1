package com.demo.banking.app.team1.service;

import com.demo.banking.app.team1.dto.TransferRequestDto;
import com.demo.banking.app.team1.dto.TransferResponseDto;
import com.demo.banking.app.team1.model.Account;
import com.demo.banking.app.team1.model.Transaction;
import com.demo.banking.app.team1.model.Type;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
public class TransferServiceImpl implements TransferService {
    private final AccountService accountService;

    public TransferServiceImpl(AccountService accountService){
        this.accountService = accountService;
    }

    @Override
    public synchronized TransferResponseDto transfer(TransferRequestDto request) {
        if (request.getFromAccountId().equals(request.getToAccountId())) {
            throw new IllegalArgumentException("Cannot transfer to the same account.");
        }

        Account fromAccount = accountService.getAccountById(request.getFromAccountId())
                .orElseThrow(()->new NoSuchElementException(
                        "Account not found with id: " + request.getFromAccountId()
                ));

        Account toAccount = accountService.getAccountById(request.getToAccountId())
                .orElseThrow(() -> new NoSuchElementException(
                        "Account not found with id: " + request.getToAccountId()
                ));

        double amount = request.getAmount();

        if (fromAccount.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient funds in the account " + fromAccount.getId());
        }

        accountService.updateBalance(fromAccount.getId(), fromAccount.getBalance() - amount);
        accountService.updateBalance(toAccount.getId(), toAccount.getBalance() + amount);

        String note = request.getNote();

        Transaction withdrawl = new Transaction(fromAccount, Type.WITHDRAWAL, note, amount);
        Transaction deposit = new Transaction(toAccount, Type.DEPOSIT, note, amount);

        accountService.addTransaction(fromAccount.getId(), withdrawl);
        accountService.addTransaction(toAccount.getId(), deposit);

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
