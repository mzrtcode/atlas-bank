package com.mzrt.atlas_bank.application.query;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionReadModel(
        Long id,
        String type, // DEPOSIT, WITHDRAWAL, TRANSFER
        Long sourceAccountId,
        Long targetAccountId,
        BigDecimal amount,
        BigDecimal fee,
        String status, // PENDING, EXECUTED, REJECTED
        LocalDateTime createdAt
) { }
