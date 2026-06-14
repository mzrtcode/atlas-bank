package com.mzrt.atlas_bank.domain.model;

public class FraudCheckException extends RuntimeException {
    public FraudCheckException(String reason) {
        super(reason);
    }
}
