package com.mzrt.atlas_bank.transaction.service.transfer;

import com.mzrt.atlas_bank.transaction.model.Transaction;

import java.math.BigDecimal;

public interface ITransferService {
    Transaction execute(Long fromAccountId, Long toAccountId, BigDecimal amount);
}
