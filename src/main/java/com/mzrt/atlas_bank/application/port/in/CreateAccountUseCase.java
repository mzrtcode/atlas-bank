package com.mzrt.atlas_bank.application.port.in;

import com.mzrt.atlas_bank.account.model.Account;

public interface CreateAccountUseCase {
    Account execute(Account account);
}
