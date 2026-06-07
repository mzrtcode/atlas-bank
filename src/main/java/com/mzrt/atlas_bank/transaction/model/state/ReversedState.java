package com.mzrt.atlas_bank.transaction.model.state;

import com.mzrt.atlas_bank.transaction.model.TransactionStatus;

public record ReversedState() implements TransactionState {
    @Override
    public TransactionStatus status() {
        return TransactionStatus.REVERSED;
    }
}
