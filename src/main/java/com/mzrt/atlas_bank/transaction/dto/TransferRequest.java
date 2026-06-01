package com.mzrt.atlas_bank.transaction.dto;

import com.mzrt.atlas_bank.transaction.validation.DifferentAccounts;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

@DifferentAccounts
public record TransferRequest(
        @NotNull(message = "La cuenta origen es obligatorio")
        Long fromAccountId,

        @NotNull(message = "La cuenta destino es obligatoria")
        Long toAccountId,

        @NotNull(message = "El monto es obligatorio")
        @Positive(message = "El monto no puede ser negativo")
        BigDecimal amount
) { }
