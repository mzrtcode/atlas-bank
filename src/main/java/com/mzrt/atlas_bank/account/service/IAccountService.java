package com.mzrt.atlas_bank.account.service;

import com.mzrt.atlas_bank.domain.model.account.Account;

import java.util.List;

public interface IAccountService {

    Account create(Account account);
    List<Account> findAll();
    Account findById(Long id);
}
