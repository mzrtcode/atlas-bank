package com.mzrt.atlas_bank.account.service.fee;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DefaultFeeCalculator implements FeeCalculator {
    @Override
    public boolean supports(String accountType) {
        return true;
    }

    @Override
    public BigDecimal calculate(BigDecimal amount) {
        return BigDecimal.ZERO;
    }
}
