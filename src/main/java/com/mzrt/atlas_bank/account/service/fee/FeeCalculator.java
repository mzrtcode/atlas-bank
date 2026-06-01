package com.mzrt.atlas_bank.account.service.fee;

import java.math.BigDecimal;

public interface FeeCalculator {
    boolean supports(String accountType);
    BigDecimal calculate(BigDecimal amount);
}
