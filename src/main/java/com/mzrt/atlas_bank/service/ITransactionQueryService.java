package com.mzrt.atlas_bank.service;

import com.mzrt.atlas_bank.model.Transaction;

import java.util.List;

public interface ITransactionQueryService {
    List<Transaction> getByAccountId(Long accountId);
}
