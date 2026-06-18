package com.mzrt.atlas_bank.application.service;

import com.mzrt.atlas_bank.application.command.CreateAccountCommand;
import com.mzrt.atlas_bank.application.port.in.CreateAccountUseCase;
import com.mzrt.atlas_bank.application.port.in.GetAccountUseCase;
import com.mzrt.atlas_bank.application.port.in.ListAccountUseCase;
import com.mzrt.atlas_bank.domain.model.account.Account;
import com.mzrt.atlas_bank.domain.model.shared.Currency;
import com.mzrt.atlas_bank.domain.model.shared.Email;
import com.mzrt.atlas_bank.domain.model.shared.Money;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class CachedAccountService implements GetAccountUseCase, ListAccountUseCase, CreateAccountUseCase {

    private final GetAccountUseCase getAccountUseCase;
    private final ListAccountUseCase listAccountUseCase;
    private final CreateAccountUseCase createAccountUseCase;

    private final Map<Long, Account> cache = new ConcurrentHashMap<>();

    public CachedAccountService(
            @Qualifier("auditableAccountService")GetAccountUseCase getAccountUseCase,
            @Qualifier("auditableAccountService")ListAccountUseCase listAccountUseCase,
            @Qualifier("auditableAccountService")CreateAccountUseCase createAccountUseCase) {
        this.getAccountUseCase = getAccountUseCase;
        this.listAccountUseCase = listAccountUseCase;
        this.createAccountUseCase = createAccountUseCase;
    }

    @Override
    public Account create(CreateAccountCommand command) {

        Account newAccount = createAccountUseCase.create(command);
        cache.put(newAccount.getId(), newAccount);
        log.info("Cuenta {} agregada al cache", newAccount.getId());
        return newAccount;
    }

    @Override
    public List<Account> findAll() {
        return listAccountUseCase.findAll();
    }

    @Override
    public Account findById(Long id) {
        Account cached = cache.get(id);

        if(cached != null){
            log.info("Cuenta {} encotrada en cache - NO se llamara al servicio real", id);
            return cached;
        }

        log.info("Cuenta {} no esta en cache - Delegando al servicio real", id);
        Account account = getAccountUseCase.findById(id);
        cache.put(id, account);
        return account;
    }
}
