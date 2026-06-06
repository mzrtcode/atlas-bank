package com.mzrt.atlas_bank.transaction.fraud;

import java.math.BigDecimal;

public interface FraudChecker {
    FraudCheckResult check(Long accountId, BigDecimal amount);
}
