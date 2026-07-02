package com.demo.banking.app.team1.dto;

import com.demo.banking.app.team1.model.Transaction;
import com.demo.banking.app.team1.model.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponseDto {

    private long id;
    private long accountId;
    private Type type;
    private double amount;
    private LocalDateTime createdAt;
    private String note;

    public static TransactionResponseDto from(Transaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction cannot be null");
        }

        return new TransactionResponseDto(
                transaction.getId(),
                transaction.getAccount().getId(),
                transaction.getType(),
                transaction.getAmount(),
                transaction.getCreatedAt(),
                transaction.getNote()
        );
    }
}
