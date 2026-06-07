package com.mzrt.atlas_bank.transaction.model.state;

import com.mzrt.atlas_bank.transaction.model.TransactionStatus;

public record ExecutedState() implements TransactionState {
    @Override
    public TransactionStatus status() {
        return TransactionStatus.EXECUTED;
    }

    @Override
    public TransactionState reverse() {
        return new ReversedState();
    }

}
