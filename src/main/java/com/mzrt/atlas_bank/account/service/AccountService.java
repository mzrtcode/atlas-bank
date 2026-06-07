package com.mzrt.atlas_bank.account.service;

import com.mzrt.atlas_bank.account.exception.AccountNotFoundException;
import com.mzrt.atlas_bank.account.model.Account;
import com.mzrt.atlas_bank.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService implements IAccountService {

    private final AccountRepository accountRepository;

    @Transactional
    @Override
    public Account create(Account account){
        return accountRepository.save(account);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Account> findAll(){
        return accountRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    @Cacheable(value = "accounts", key = "#id")
    public Account findById(Long id){
        return accountRepository.findById(id).orElseThrow(
                () -> new AccountNotFoundException(id)
        );
    }

}
