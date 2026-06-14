package com.mzrt.atlas_bank.domain.model.exception;

import java.math.BigDecimal;

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(Long accountId, BigDecimal balance, BigDecimal amount) {
        super("La cuenta con id %d tiene saldo %s y se intentó transferir %s"
                .formatted(accountId, balance, amount));
    }
}
