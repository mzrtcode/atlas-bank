package com.mzrt.atlas_bank.domain.model.transaction;

import com.mzrt.atlas_bank.domain.model.transaction.state.*;
import com.mzrt.atlas_bank.domain.event.TransactionExecutedEvent;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Transaction {

    @EqualsAndHashCode.Include
    private Long id;
    private TransactionType type;
    private Long sourceAccountId;
    private Long targetAccountId;
    private BigDecimal amount;
    private BigDecimal fee;
    private TransactionStatus status;
    private LocalDateTime createdAt;
    private TransactionState state;
    private String createdBy;
    private String description;

    @Builder.Default
    private final List<Object> domainEvents = new ArrayList<>();

    public void initDefaults() {
        if(createdAt == null) this.createdAt = LocalDateTime.now();
        if (this.status == null) this.status = TransactionStatus.EXECUTED;
    }

    public TransactionState getState() {
        if(state == null){

            state = switch(status){
                case PENDING -> new PendingState();
                case VALIDATED -> new ValidatedState();
                case EXECUTED -> new ExecutedState();
                case REJECTED -> new RejectedState();
                case REVERSED -> new ReversedState();
            };
        }
        return state;
    }

    public void advancedTo(TransactionState newState){
        state = newState;
        status = newState.status();
    }

    public void maskAsExecuted(){
        domainEvents.add(new TransactionExecutedEvent(
                id,
                type,
                sourceAccountId,
                targetAccountId,
                amount,
                fee
        ));
    }

    public void executeTransfer(){
        advancedTo(getState().validate());
        advancedTo(getState().execute());
        maskAsExecuted();
    }

    public List<Object> clearDomainEvents(){
        return Collections.unmodifiableList(domainEvents);
    }

    public void clearObjets(){
        domainEvents.clear();
    }
}