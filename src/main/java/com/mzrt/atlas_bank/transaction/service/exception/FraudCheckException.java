package com.mzrt.atlas_bank.transaction.service.exception;

public class FraudCheckException extends RuntimeException {
  public FraudCheckException(String message) {
    super(message);
  }
}
