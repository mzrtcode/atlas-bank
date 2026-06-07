package com.mzrt.atlas_bank.transaction.validation.chain;

import com.mzrt.atlas_bank.transaction.fraud.FraudCheckResult;
import com.mzrt.atlas_bank.transaction.fraud.FraudChecker;
import com.mzrt.atlas_bank.transaction.service.exception.FraudCheckException;
import com.mzrt.atlas_bank.transaction.service.transfer.TransferContext;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(3)
@RequiredArgsConstructor
public class FraudValidator implements TransferValidator{

    private final FraudChecker fraudChecker;

    @Override
    public void validate(TransferContext context) {
        FraudCheckResult checkResult = fraudChecker.check(context.accountFrom().getId(), context.amount());

        if (checkResult.blocked()) throw new FraudCheckException(checkResult.reason());
    }
}
