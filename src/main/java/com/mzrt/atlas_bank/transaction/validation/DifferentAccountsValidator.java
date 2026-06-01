package com.mzrt.atlas_bank.transaction.validation;

import com.mzrt.atlas_bank.transaction.dto.TransferRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DifferentAccountsValidator implements ConstraintValidator<DifferentAccounts, TransferRequest> {
    @Override
    public boolean isValid(TransferRequest transferRequest, ConstraintValidatorContext context) {
        if(transferRequest.fromAccountId() == null || transferRequest.toAccountId() == null) {
            return true;
        }

        return !transferRequest.fromAccountId().equals(transferRequest.toAccountId());
    }
}
