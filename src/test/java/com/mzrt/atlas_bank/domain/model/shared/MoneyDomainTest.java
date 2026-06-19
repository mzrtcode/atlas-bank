package com.mzrt.atlas_bank.domain.model.shared;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class MoneyDomainTest {

    @Test
    @DisplayName("Debe sumar dos montos de la misma moneda")
    void shouldAddSameCurrency(){
        Money a = Money.of(new BigDecimal("100"), Currency.COP);
        Money b = Money.of(new BigDecimal("250.50"), Currency.COP);

        Money result = a.add(b);

        assertEquals(Money.of(new BigDecimal("350.50"), Currency.COP), result);

    }

    @Test
    @DisplayName("Debe rechazar operacion de monedas distintas")
    void shouldRejectDifferentCurrency(){
        Money usd = Money.of(new BigDecimal("100"), Currency.USD);
        Money cop = Money.of(new BigDecimal("250.50"), Currency.COP);

        assertThrows(IllegalArgumentException.class, () -> usd.subtract(cop));
    }

    @Test
    @DisplayName("Debe detectar monto negativo")
    void shouldDetectNegativeAmount(){
        Money money = Money.of(new BigDecimal("-250"), Currency.COP);
        assertTrue(money.isNegative());
    }

    @Test
    @DisplayName("Comparar montos correctamente")
    void shouldCompareAmountsCorrectly(){
        Money hundred = Money.of(new BigDecimal("100"), Currency.COP);
        Money fifty = Money.of(new BigDecimal("50"), Currency.COP);

        assertTrue(hundred.isGreaterThan(fifty));
        assertTrue(fifty.isLessThan(hundred));
    }


    @Test
    @DisplayName("Money.zero debe tener monto cero")
    void shouldCreateZeroMoney() {
        Money zero = Money.zero(Currency.COP);
        assertEquals(new BigDecimal("0.00"), zero.getAmount());
        assertFalse(zero.isNegative());
    }




}