package com.mzrt.atlas_bank.transaction.validation.chain;

import com.mzrt.atlas_bank.domain.model.account.AccountStatus;
import com.mzrt.atlas_bank.domain.model.exception.AccountNotActiveException;
import com.mzrt.atlas_bank.transaction.service.transfer.TransferContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
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
