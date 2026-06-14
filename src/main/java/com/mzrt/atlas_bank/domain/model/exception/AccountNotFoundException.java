package com.mzrt.atlas_bank.domain.model.exception;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(Long id) {
        super("No se encontró la cuenta con id %d".formatted(id));
    }
}
