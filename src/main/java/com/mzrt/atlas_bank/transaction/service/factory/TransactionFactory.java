package com.mzrt.atlas_bank.transaction.service.factory;

import com.mzrt.atlas_bank.transaction.model.Transaction;
import com.mzrt.atlas_bank.transaction.model.TransactionStatus;
import com.mzrt.atlas_bank.transaction.model.TransactionType;
import com.mzrt.atlas_bank.transaction.model.state.PendingState;
import com.mzrt.atlas_bank.transaction.service.transfer.TransferContext;

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
