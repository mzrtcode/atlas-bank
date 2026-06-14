package com.mzrt.atlas_bank.transaction.service;

import com.mzrt.atlas_bank.domain.model.transaction.Transaction;

import java.util.List;

public interface ITransactionQueryService {
    List<Transaction> getByAccountId(Long accountId);
}
