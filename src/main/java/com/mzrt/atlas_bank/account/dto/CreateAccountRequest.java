package com.mzrt.atlas_bank.account.dto;

import com.mzrt.atlas_bank.account.model.AccountType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record CreateAccountRequest(
        @NotBlank(message = "El numero de cuenta es obligatorio")
        String accountNumber,

        @NotBlank(message = "El nombre del titular es obligatorio")
        String ownerName,

        @NotBlank(message = "El email es obligatorio")
        @Email(message = "Email no valido")
        String email,

        @NotNull(message = "El tipo de cuenta es obligatorio")
        AccountType type,

        @PositiveOrZero(message = "El saldo no puede ser negativo")
        BigDecimal balance
) { }
