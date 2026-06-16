package com.mzrt.atlas_bank.application.service;

import com.mzrt.atlas_bank.application.port.out.TransactionRepositoryPort;
import com.mzrt.atlas_bank.domain.model.transaction.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionQueryService implements ITransactionQueryService{

    private final TransactionRepositoryPort transactionRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Transaction> getByAccountId(Long accountId) {
        return transactionRepository
                .findBySourceAccountIdOrTargetAccountId(accountId, accountId);
    }
}
