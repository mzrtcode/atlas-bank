package com.mzrt.atlas_bank.infrastructure.adapter.listener;

import com.mzrt.atlas_bank.domain.event.TransactionExecutedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuditListener {

    @EventListener(TransactionExecutedEvent.class)
    public void onTransactionExecuted(TransactionExecutedEvent event) {
        log.info("Registrando auditoria - {} de cuenta ${} a cuenta #{} por ${}",
                event.type(), event.sourceAccountId(), event.targetAccountId(), event.amount()
                );
    }
}
