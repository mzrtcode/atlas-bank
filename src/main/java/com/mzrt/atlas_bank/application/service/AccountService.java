package com.mzrt.atlas_bank.application.service;

import com.mzrt.atlas_bank.application.port.in.CreateAccountUseCase;
import com.mzrt.atlas_bank.application.port.in.GetAccountUseCase;
import com.mzrt.atlas_bank.application.port.in.ListAccountUseCase;
import com.mzrt.atlas_bank.application.port.out.AccountRepositoryPort;
import com.mzrt.atlas_bank.domain.exception.AccountNotFoundException;
import com.mzrt.atlas_bank.domain.model.account.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService implements GetAccountUseCase, ListAccountUseCase, CreateAccountUseCase {

    private final AccountRepositoryPort accountRepository;

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
