package com.mzrt.atlas_bank.transaction.model.state;

import com.mzrt.atlas_bank.transaction.model.TransactionStatus;

public record ValidatedState() implements TransactionState {
    @Override
    public TransactionStatus status() {
        return TransactionStatus.VALIDATED;
    }

    @Override
    public TransactionState execute() {
        return new ExecutedState();
    }

    @Override
    public TransactionState reject() {
        return new RejectedState();
    }

}
