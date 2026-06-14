package com.mzrt.atlas_bank.transaction.service;

import com.mzrt.atlas_bank.domain.model.transaction.Transaction;
import com.mzrt.atlas_bank.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionQueryService implements ITransactionQueryService{

    private final TransactionRepository transactionRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Transaction> getByAccountId(Long accountId) {
        return transactionRepository
                .findBySourceAccountIdOrTargetAccountId(accountId, accountId);
    }
}
