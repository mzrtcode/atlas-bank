package com.mzrt.atlas_bank.account.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Account {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
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
