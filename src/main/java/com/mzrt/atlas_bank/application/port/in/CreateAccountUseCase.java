package com.mzrt.atlas_bank.application.port.in;

import com.mzrt.atlas_bank.domain.model.account.Account;

public interface CreateAccountUseCase {
    Account execute(Account account);
}
