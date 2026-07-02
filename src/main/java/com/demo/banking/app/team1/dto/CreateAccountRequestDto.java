package com.demo.banking.app.team1.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class CreateAccountRequestDto
{
    @NotBlank(message = "IBAN cannot be empty.")
    private String iban;

    @NotBlank(message = "Owner name cannot be empty.")
    private String ownerName;

    @PositiveOrZero(message = "Balance cannot be negative.")
    private double balance;


}

