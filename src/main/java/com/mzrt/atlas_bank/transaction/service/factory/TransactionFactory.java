package com.mzrt.atlas_bank.transaction.service.factory;

import com.mzrt.atlas_bank.transaction.model.Transaction;
import com.mzrt.atlas_bank.transaction.model.TransactionStatus;
import com.mzrt.atlas_bank.transaction.model.TransactionType;
import com.mzrt.atlas_bank.transaction.model.state.PendingState;
import com.mzrt.atlas_bank.transaction.service.transfer.TransferContext;

import java.math.BigDecimal;

public class TransactionFactory {

    public static Transaction createTransfer(TransferContext context, BigDecimal fee) {

        Transaction transaction = new Transaction();
        transaction.setType(TransactionType.TRANSFER);
        transaction.setSourceAccountId(context.accountFrom().getId());
        transaction.setTargetAccountId(context.accountTo().getId());
        transaction.setAmount(context.amount());
        transaction.setFee(fee);
        transaction.setStatus(TransactionStatus.PENDING);
        transaction.advancedTo(new PendingState());
        return transaction;
    }
}
