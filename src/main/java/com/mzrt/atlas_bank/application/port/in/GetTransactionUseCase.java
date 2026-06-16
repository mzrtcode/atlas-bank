package com.mzrt.atlas_bank.application.port.in;

import com.mzrt.atlas_bank.domain.model.transaction.Transaction;

import java.util.List;

public interface GetTransactionUseCase {
    List<Transaction> getByAccountId(Long accountId);

}
