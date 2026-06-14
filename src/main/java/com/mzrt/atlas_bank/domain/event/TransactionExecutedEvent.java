package com.mzrt.atlas_bank.domain.event;

import com.mzrt.atlas_bank.domain.model.transaction.TransactionType;

import java.math.BigDecimal;

public record TransactionExecutedEvent(
        Long transactionId,
        TransactionType type,
        Long sourceAccountId,
        Long targetAccountId,
        BigDecimal amount,
        BigDecimal fee
) { }
