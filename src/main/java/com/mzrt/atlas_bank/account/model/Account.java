package com.mzrt.atlas_bank.account.model;

import com.mzrt.atlas_bank.shared.model.Currency;
import com.mzrt.atlas_bank.shared.model.Money;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "accounts")
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Account {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "account_number", nullable = false, unique = true)
    private String accountNumber;

    @Column(name = "owner_name", nullable = false)
    private String ownerName;

    @Column(nullable = false)
    private String email;

    @Embedded
    @AttributeOverrides(
            {
                    @AttributeOverride(name = "amount", column = @Column(name = "balance", nullable = false)),
                    @AttributeOverride(name = "currency", column = @Column(name = "currency", length = 3, nullable = false))
            }
    )
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AccountType type; //Savings, Checking

    @Column(nullable = false)
    private Money balance;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AccountStatus status; //Active, Closed, Frozen

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist(){
        if (status == null) this.status = AccountStatus.ACTIVE ;
        if (balance == null) balance = Money.zero(Currency.COP);
        createdAt = LocalDateTime.now();
    }
}
