package com.mzrt.atlas_bank.transaction.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TransactionType type; // DEPOSIT, WITHDRAWAL, TRANSFER

    @Column(name = "source_account_id")
    private Long sourceAccountId;

    @Column(name = "target_account_id")
    private Long targetAccountId;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private BigDecimal fee;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TransactionStatus status; // PENDING, EXECUTED, REJECTED

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private String createdBy;
    private String Description;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        if (this.status == null) this.status = TransactionStatus.EXECUTED;
    }
}