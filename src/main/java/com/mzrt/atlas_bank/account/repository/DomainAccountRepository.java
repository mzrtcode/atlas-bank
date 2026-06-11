package com.mzrt.atlas_bank.account.repository;

import com.mzrt.atlas_bank.account.model.Account;

import java.util.List;
import java.util.Optional;

public interface DomainAccountRepository {
    Account save(Account account);
    List<Account> findAll();
    Optional<Account> findById(Long id);

}
