package com.mzrt.atlas_bank.account.service;

import com.mzrt.atlas_bank.domain.model.account.Account;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@Primary
public class AuditableAccountService implements IAccountService{

    private final IAccountService delegate;

    public AuditableAccountService(@Qualifier("accountService") IAccountService accountService) {
        this.delegate = accountService;
    }

    @PostConstruct
    public void init(){
        log.info("clase real del accountService: {}", delegate.getClass().getName());
    }


    @Override
    public Account create(Account account) {
        log.info("Creando una cuenta - numero: {}, titular: {}",
                account.getAccountNumber(), account.getOwnerName());

        Account createdAccount = delegate.create(account);
        log.info("Cuenta creada exitosamente - ID: {}", createdAccount.getId());

        return createdAccount;
    }

    @Override
    public List<Account> findAll() {
        return delegate.findAll();
    }

    @Override
    public Account findById(Long id) {
        return delegate.findById(id);
    }
}
