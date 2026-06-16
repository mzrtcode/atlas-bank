package com.mzrt.atlas_bank.infrastructure.adapter.out.persistence;

import com.mzrt.atlas_bank.application.port.out.TransactionRepositoryPort;
import com.mzrt.atlas_bank.domain.model.transaction.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JpaTransactionRepositoryAdapter implements TransactionRepositoryPort {

    private final SpringDataTransactionRepository repository;
    private final TransactionPersistenceMapper mapper;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public Transaction save(Transaction transaction) {
        transaction.initDefaults();
        TransactionJpaEntity entity = mapper.toJpaEntity(transaction);
        TransactionJpaEntity saved = repository.save(entity);
        transaction.getDomainEvents().forEach(eventPublisher::publishEvent);
        transaction.clearDomainEvents();
        return mapper.toDomain(saved);
    }

    @Override
    public List<Transaction> findBySourceAccountIdOrTargetAccountId(Long sourceId, Long targetId) {
        return repository.findBySourceAccountIdOrTargetAccountId(sourceId, targetId)
                .stream().map(mapper::toDomain)
                .toList();
    }
}
