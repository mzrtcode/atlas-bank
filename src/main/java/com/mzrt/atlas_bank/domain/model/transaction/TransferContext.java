package com.mzrt.atlas_bank.domain.model.transaction;

import com.mzrt.atlas_bank.domain.model.account.Account;

import java.math.BigDecimal;

public record TransferContext(
     Account accountFrom,
     Account accountTo,
     BigDecimal amount
) { }
