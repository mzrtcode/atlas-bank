package com.mzrt.atlas_bank.transaction.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponse(
        Long id,
        String type, // DEPOSIT, WITHDRAWAL, TRANSFER
        Long sourceAccountId,
        Long targetAccountId,
        BigDecimal amount,
        BigDecimal fee,
        String status, // PENDING, EXECUTED, REJECTED
        LocalDateTime createdAt
) { }
