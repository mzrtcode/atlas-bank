package com.mzrt.atlas_bank.transaction.service.listener;

import com.mzrt.atlas_bank.domain.event.TransactionExecutedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NotificationListener {

    @EventListener(TransactionExecutedEvent.class)
    public void onTransactionExecuted(TransactionExecutedEvent event) {
        log.info("Enviando comprobante de {} por ${} - Transaccion #{}",
                event.type(),
                event.amount(),
                event.transactionId());
    }
}
