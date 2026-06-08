package com.mzrt.atlas_bank.shared.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.lang.classfile.constantpool.ModuleEntry;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
public class Money {

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false, length = 3)
    private Currency currency;

    private Money(BigDecimal amount, Currency currency) {

        if(amount==null){
            throw new IllegalArgumentException("El monto no puede ser nulo");
        }
        this.amount = amount.setScale(2, RoundingMode.HALF_UP);
        this.currency = currency;
    }

    public static Money of(BigDecimal amount, Currency currency){
        return new Money(amount, currency);
    }

    public static Money zero(Currency currency){
        return new Money(BigDecimal.ZERO, currency);
    }

    public Money add(Money other){
        validateSameCurrency(other);
        return Money.of(this.amount.add(other.amount), this.currency);
    }

    public Money substract(Money other){
        validateSameCurrency(other);
        return Money.of(this.amount.subtract(other.amount), this.currency);
    }

    public boolean isGreaterThan(Money other){
        validateSameCurrency(other);
        return this.amount.compareTo(other.amount) > 0;
    }

    public boolean isNegative(){
        return amount.compareTo(BigDecimal.ZERO) < 0;
    }


    private void validateSameCurrency(Money money){
        if(currency != money.currency){
            throw new IllegalArgumentException("No se pueden operar montos en monedas distintas");
        }
    }

    @Override
    public String toString() {
        return amount.toPlainString() + " " + currency;
    }
}
