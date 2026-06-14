package com.mzrt.atlas_bank.transaction.fraud;

import com.mzrt.atlas_bank.domain.model.shared.FraudCheckResult;

import java.math.BigDecimal;

public interface FraudChecker {
    FraudCheckResult check(Long accountId, BigDecimal amount);
}
