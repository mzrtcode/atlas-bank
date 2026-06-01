package com.mzrt.atlas_bank.account.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Account {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String accountNumber;
    private String ownerName;
    private String email;
    private String type; //Savings, Checking
    private BigDecimal balance;
    private String status; //Active, Closed, Frozen
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist(){
        if (status == null) status = "ACTIVE";
        if (balance == null) balance= BigDecimal.ZERO;
        createdAt = LocalDateTime.now();
    }
}
