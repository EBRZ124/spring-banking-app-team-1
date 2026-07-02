package com.demo.banking.app.team1.service;

import com.demo.banking.app.team1.model.Transaction;

import java.util.List;

public interface ITransferService {

    List<Transaction> getTransactionsByAccountId(long accountId);

}
