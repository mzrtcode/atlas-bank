package com.mzrt.atlas_bank.application.port.in;

import com.mzrt.atlas_bank.application.command.TransferMoneyCommand;
import com.mzrt.atlas_bank.domain.model.transaction.Transaction;

import java.math.BigDecimal;

public interface TransferMoneyUseCase {
    Transaction transfer(TransferMoneyCommand command);
}
