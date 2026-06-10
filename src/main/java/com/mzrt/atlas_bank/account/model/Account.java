package com.mzrt.atlas_bank.account.model;

import com.mzrt.atlas_bank.shared.model.Currency;
import com.mzrt.atlas_bank.shared.model.Money;
import com.mzrt.atlas_bank.transaction.exception.InsufficientFundsException;
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AccountType type;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(
                    name = "amount",
                    column = @Column(name = "balance", nullable = false)
            ),
            @AttributeOverride(
                    name = "currency",
                    column = @Column(name = "currency", length = 3, nullable = false)
            )
    })
    private Money balance;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AccountStatus status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "customer_id")
    private Long customerId;


    @PrePersist
    public void prePersist(){
        if (status == null) this.status = AccountStatus.ACTIVE ;
        if (balance == null) balance = Money.zero(Currency.COP);
        createdAt = LocalDateTime.now();
    }

    public void deposit(Money amount){
        if(amount.isNegative()){
            throw new IllegalArgumentException("El monto a depositar no debe ser negativo");
        }
        this.balance.add(amount);
    }

    public void withDraw(Money amount){
        if(amount.isNegative()){
            throw new IllegalArgumentException("El monto a retirar no debe ser negativo");
        }

        if(amount.isGreaterThan(amount)){
            throw new InsufficientFundsException(id, balance.getAmount(), amount.getAmount());
        }

        this.balance.substract(amount);
    }


}
