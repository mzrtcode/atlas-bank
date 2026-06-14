package com.mzrt.atlas_bank.domain.validation;

import com.mzrt.atlas_bank.domain.model.transaction.TransferContext;

public interface TransferValidator {
    void validate(TransferContext context);
}
