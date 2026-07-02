package com.demo.banking.app.team1.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransferResponseDto {

    @NotNull(message = "From account ID is required")
    private Long fromAccountId;

    @NotNull(message = "To account ID is required")
    private Long toAccountId;

    @Positive(message = "Transfer amount must be greater than 0")
    private Double amount;

    @PositiveOrZero(message = "Balance amount must be greater than or equal to 0")
    private Double fromAccountNewBalance;

    @PositiveOrZero(message = "Balance amount must be greater than or equal to 0")
    private Double toAccountNewBalance;

    @PastOrPresent(message = "TimeStamp cannot be in the future")
    private LocalDateTime timeStamp;
}
