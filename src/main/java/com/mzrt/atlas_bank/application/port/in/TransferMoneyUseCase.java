package com.mzrt.atlas_bank.application.port.in;

import com.mzrt.atlas_bank.domain.model.transaction.Transaction;

import java.math.BigDecimal;

public interface TransferMoneyUseCase {
    Transaction execute(Long fromAccountId, Long toAccountId, BigDecimal amount);
}
