package com.mzrt.atlas_bank.application.port.out;

import com.mzrt.atlas_bank.domain.model.account.Account;

import java.util.List;
import java.util.Optional;

public interface AccountRepositoryPort {
    Account save(Account account);
    List<Account> findAll();
    Optional<Account> findById(Long id);
}
