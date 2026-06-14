package com.mzrt.atlas_bank.domain.model.transaction.state;

import com.mzrt.atlas_bank.domain.model.transaction.TransactionStatus;

public sealed  interface TransactionState permits PendingState, ValidatedState, ExecutedState, ReversedState, RejectedState {
    TransactionStatus status();

    default TransactionState validate(){
        throw new IllegalStateException("No se puede ejecutar una transaccion en estado " + status());
    }

    default TransactionState execute(){
        throw new IllegalStateException("No se puede ejecutar una transaccion en estado " + status());
    }

    default TransactionState reject(){
        throw new IllegalStateException("No se puede ejecutar una transaccion en estado " + status());
    }

    default TransactionState reverse(){
        throw new IllegalStateException("No se puede ejecutar una transaccion en estado " + status());
    }


}
