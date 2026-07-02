package com.demo.banking.app.team1.controller;

import com.demo.banking.app.team1.dto.AccountResponseDto;
import com.demo.banking.app.team1.dto.CreateAccountRequestDto;
import com.demo.banking.app.team1.dto.TransactionResponseDto;
import com.demo.banking.app.team1.model.Account;
import com.demo.banking.app.team1.service.AccountService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountResponseDto> createAccount(@Valid @RequestBody CreateAccountRequestDto request) {
        Account account = accountService.createAccount(
                request.getIban(),
                request.getOwnerName(),
                request.getBalance()
        );
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(account.getId())
                .toUri();

        return ResponseEntity.created(location).body(AccountResponseDto.from(account));
    }

    @GetMapping("/{id}")
    public AccountResponseDto getAccountById(@PathVariable @Positive long id) {
        return accountService.getAccountById(id)
                .map(AccountResponseDto::from)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found with id: " + id));
    }

    @GetMapping("/{id}/transactions")
    public List<TransactionResponseDto> getTransactionsByAccountId(@PathVariable @Positive long id) {
        try {
            return accountService.getTransactionsForAccount(id)
                    .stream()
                    .map(TransactionResponseDto::from)
                    .toList();
        } catch (NoSuchElementException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
        }
    }
}
