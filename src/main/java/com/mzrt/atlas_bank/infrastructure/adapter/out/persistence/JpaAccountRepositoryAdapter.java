package com.mzrt.atlas_bank.infrastructure.adapter.out.persistence;

import com.mzrt.atlas_bank.application.port.out.AccountRepositoryPort;
import com.mzrt.atlas_bank.domain.model.account.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaAccountRepositoryAdapter implements AccountRepositoryPort {

    private final SpingDataAccountRepository repository;
    private final AccountPersistenceMapper mapper;

    @Override
    public Account save(Account account) {
        account.initDefaults();
        AccountJpaEntity entity = mapper.toJpaEntity(account);
        AccountJpaEntity saved = repository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public List<Account> findAll() {
        return repository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public Optional<Account> findById(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }
}
