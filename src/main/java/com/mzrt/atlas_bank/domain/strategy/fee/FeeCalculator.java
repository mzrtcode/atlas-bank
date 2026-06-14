package com.mzrt.atlas_bank.domain.strategy.fee;

import com.mzrt.atlas_bank.domain.model.account.AccountType;

import java.math.BigDecimal;

public interface FeeCalculator {
    boolean supports(AccountType accountType);
    BigDecimal calculate(BigDecimal amount);
}
