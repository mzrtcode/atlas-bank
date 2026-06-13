package com.mzrt.atlas_bank.application.port.out;

import com.mzrt.atlas_bank.transaction.fraud.FraudCheckResult;

import java.math.BigDecimal;

public interface FraudCheckPort {
    FraudCheckResult check(Long accountId, BigDecimal amount);
}
