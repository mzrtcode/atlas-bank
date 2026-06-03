package com.mzrt.atlas_bank.transaction.service;

import com.mzrt.atlas_bank.account.model.Account;

import java.math.BigDecimal;

public record TransferContext(
     Account accountFrom,
     Account accountTo,
     BigDecimal amount
) { }
