package com.mzrt.atlas_bank.infrastructure.adapter.out.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

@Embeddable
@Getter
@NoArgsConstructor
public class Email {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");

    @Column(nullable = false)
    private String value;

    private Email(String value){
        if (value == null || !EMAIL_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("Email invalido: " + value);
        }
        this.value = value;
    }

    public static Email of(String value){
        return new Email(value.toLowerCase().trim());
    }

    @Override
    public String toString() {
        return "Email: " + value;
    }
}
