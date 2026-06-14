package com.mzrt.atlas_bank.transaction.repository;

import com.mzrt.atlas_bank.domain.model.transaction.Transaction;

import java.util.List;

public interface TransactionDomainRepository {
    List<Transaction> findBySourceAccountIdOrTargetAccountId(Long sourceId, Long targetId);
    Transaction save(Transaction transaction);
}
