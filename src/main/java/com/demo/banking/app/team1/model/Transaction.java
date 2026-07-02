package com.demo.banking.app.team1.model;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

@Getter
@Setter
@ToString
public class Transaction {
    private static final AtomicLong ID_GENERATOR = new AtomicLong(1);

    @Setter(value = AccessLevel.NONE)
    private long id;

    @NotNull
    private Account account;

    @NotNull
    private Type type;

    @Setter(value = AccessLevel.NONE)
    private LocalDateTime createdAt;

    @NotNull
    private String note;

    public Transaction(Account account, Type type, String note){
        this.account = account;
        this.type = type;
        this.note = note;
        this.id = ID_GENERATOR.getAndIncrement();
        this.createdAt = LocalDateTime.now();
    }
}
