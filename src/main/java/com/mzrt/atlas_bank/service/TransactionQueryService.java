package com.mzrt.atlas_bank.service;

import com.mzrt.atlas_bank.model.Transaction;
import com.mzrt.atlas_bank.repository.TransactionRepository;
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
