package com.demo.banking.app.team1.service;

import com.demo.banking.app.team1.model.Account;

public interface IAccountService {
    Account getAccountById(long id);
    void updateBalance(long id, double newBalance);
}
