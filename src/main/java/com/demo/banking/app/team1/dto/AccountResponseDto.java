package com.demo.banking.app.team1.dto;

import com.demo.banking.app.team1.model.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponseDto {

    private long id;
    private String iban;
    private String ownerName;
    private double balance;

    public static AccountResponseDto from(Account account) {
        return new AccountResponseDto(
                account.getId(),
                account.getIban(),
                account.getOwnerName(),
                account.getBalance()
        );
    }
}
