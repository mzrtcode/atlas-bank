package com.mzrt.atlas_bank.domain.validation;

import com.mzrt.atlas_bank.domain.model.account.AccountStatus;
import com.mzrt.atlas_bank.domain.exception.AccountNotActiveException;
import com.mzrt.atlas_bank.domain.model.transaction.TransferContext;

public class AccountStatusValidator implements TransferValidator{
    @Override
    public void validate(TransferContext context) {
        if (context.accountFrom().getStatus() != AccountStatus.ACTIVE) {
            throw new AccountNotActiveException(context.accountFrom().getId(), context.accountTo().getStatus().name());
        }
        if (context.accountTo().getStatus() != AccountStatus.ACTIVE) {
            throw new AccountNotActiveException(context.accountTo().getId(), context.accountTo().getStatus().name());
        }
    }
}
