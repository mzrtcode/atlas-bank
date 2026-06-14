package com.mzrt.atlas_bank.domain.model.exception;

public class AccountNotActiveException extends RuntimeException {
    public AccountNotActiveException(Long accountId, String status) {
        super("La cuenta %d no esta activa. Estado actual: %s".formatted(accountId, status));
    }
}
