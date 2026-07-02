package com.demo.banking.app.team1.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.concurrent.atomic.AtomicLong;

@Getter
@Setter
@ToString
public class Account {

    private static final AtomicLong ID_GENERATOR = new AtomicLong(1);

    @Setter(value = AccessLevel.NONE)
    private long id;

    @NotNull
    private String iban;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z]{2,50}$")
    private String ownerName;

    @Min(0)
    private double balance;

    public Account(String iban, String ownerName, double balance){
        this.id = ID_GENERATOR.getAndIncrement();
        this.iban = iban;
        this.ownerName = ownerName;
        this.balance = balance;
    }
}
