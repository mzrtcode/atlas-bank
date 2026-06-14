package com.mzrt.atlas_bank.transaction.service.factory;

import com.mzrt.atlas_bank.domain.model.transaction.Transaction;
import com.mzrt.atlas_bank.domain.model.transaction.TransactionStatus;
import com.mzrt.atlas_bank.domain.model.transaction.TransactionType;
import com.mzrt.atlas_bank.domain.model.transaction.state.PendingState;
import com.mzrt.atlas_bank.domain.model.transaction.TransferContext;

import java.math.BigDecimal;

public class TransactionFactory {

    public static Transaction createTransfer(TransferContext context, BigDecimal fee) {

        Transaction transaction = Transaction.builder()
                .type(TransactionType.TRANSFER)
                .sourceAccountId(context.accountFrom().getId())
                .targetAccountId(context.accountTo().getId())
                .amount(context.amount())
                .fee(fee)
                .status(TransactionStatus.PENDING)
                .build();

        transaction.advancedTo(new PendingState());
        return transaction;
    }
}
