package com.mzrt.atlas_bank.infrastructure.adapter.in.rest.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AccountResponse(
        Long id,
        String accountNumber,
        String ownerName,
        String email,
        String type, //Savings, Checking
        BigDecimal balance,
        String status, //Active, Closed, Frozen
        LocalDateTime createdAt
) { }
