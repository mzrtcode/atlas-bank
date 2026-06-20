package com.mzrt.atlas_bank.application.port.in;

import com.mzrt.atlas_bank.application.command.CloseAccountCommand;
import com.mzrt.atlas_bank.domain.model.account.Account;

public interface CloseAccountUseCase {
    Account close(CloseAccountCommand command);
}
