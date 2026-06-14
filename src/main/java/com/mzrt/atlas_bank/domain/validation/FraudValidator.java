package com.mzrt.atlas_bank.domain.validation;

import com.mzrt.atlas_bank.application.port.out.FraudCheckPort;
import com.mzrt.atlas_bank.domain.model.shared.FraudCheckResult;
import com.mzrt.atlas_bank.domain.model.FraudCheckException;
import com.mzrt.atlas_bank.domain.model.transaction.TransferContext;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(3)
@RequiredArgsConstructor
public class FraudValidator implements TransferValidator{

    private final FraudCheckPort fraudChecker;

    @Override
    public void validate(TransferContext context) {
        FraudCheckResult checkResult = fraudChecker.check(context.accountFrom().getId(), context.amount());

        if (checkResult.blocked()) throw new FraudCheckException(checkResult.reason());
    }
}
