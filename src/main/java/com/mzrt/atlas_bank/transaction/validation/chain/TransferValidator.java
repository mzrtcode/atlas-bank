package com.mzrt.atlas_bank.transaction.validation.chain;

import com.mzrt.atlas_bank.transaction.service.transfer.TransferContext;

public interface TransferValidator {
    void validate(TransferContext context);
}
