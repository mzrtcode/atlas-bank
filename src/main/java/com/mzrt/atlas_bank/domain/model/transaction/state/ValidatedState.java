package com.mzrt.atlas_bank.domain.model.transaction.state;

import com.mzrt.atlas_bank.domain.model.transaction.TransactionStatus;

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
