package com.mzrt.atlas_bank.transaction.service.listener;

import com.mzrt.atlas_bank.transaction.service.event.TransactionExecutedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuditListener {

    public void onTransactionExecuted(TransactionExecutedEvent event) {
        log.info("Registrando auditoria - {} de cuenta ${} a cuenta #{} por ${}",
                event.type(), event.sourceAccountId(), event.targetAccountId(), event.amount()
                );
    }
}
