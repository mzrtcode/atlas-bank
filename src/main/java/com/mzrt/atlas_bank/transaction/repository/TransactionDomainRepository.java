package com.mzrt.atlas_bank.transaction.repository;

import com.mzrt.atlas_bank.transaction.model.Transaction;

import java.util.List;

public interface TransactionDomainRepository {
    List<Transaction> findBySourceAccountIdOrTargetAccountId(Long sourceId, Long targetId);
    Transaction save(Transaction transaction);
}
