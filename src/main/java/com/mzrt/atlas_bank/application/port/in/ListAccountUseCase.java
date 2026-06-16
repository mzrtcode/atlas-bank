package com.mzrt.atlas_bank.application.port.in;

import com.mzrt.atlas_bank.domain.model.account.Account;

import java.util.List;

public interface ListAccountUseCase {
    List<Account> findAll();

}
