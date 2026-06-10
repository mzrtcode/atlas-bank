package com.mzrt.atlas_bank.transaction.validation.chain;

import com.mzrt.atlas_bank.transaction.exception.InsufficientFundsException;
import com.mzrt.atlas_bank.transaction.service.transfer.TransferContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class SufficientFundsValidator implements TransferValidator{
    @Override
    public void validate(TransferContext context) {
        if (context.accountFrom().getBalance().getAmount().compareTo(context.amount()) < 0) {
            throw new InsufficientFundsException(context.accountFrom().getId(), context.accountFrom().getBalance().getAmount(), context.amount());
        }
    }
}
