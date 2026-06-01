package com.mzrt.atlas_bank.transaction.service;

import com.mzrt.atlas_bank.transaction.model.Transaction;

import java.util.List;

public interface ITransactionQueryService {
    List<Transaction> getByAccountId(Long accountId);
}
