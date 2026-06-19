package com.mzrt.atlas_bank.domain.model.shared;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailDomainTest {


    @Test
    @DisplayName("Debe crear email con formato valido")
    void shouldCreteValidEmail(){
        Email email = Email.of("test@test.com");

        assertEquals("test@test.com", email.getValue());
    }

    @Test
    @DisplayName("Debe normalizar a minusculas")
    void shouldNormalizeToLowerCase(){
        Email email = Email.of("TEST@TEST.COM");
        assertEquals("test@test.com", email.getValue());
    }

    @Test
    @DisplayName("Debe rechazar email con formato invalido")
    void shouldRejectInvalidEmail(){
        assertThrows(IllegalArgumentException.class, () -> Email.of("TEST@TESt"));
    }

    @Test
    @DisplayName("Debe rechazar email nulo")
    void shouldRejectNullEmail(){
        assertThrows(IllegalArgumentException.class, () -> Email.of(null));
    }

}