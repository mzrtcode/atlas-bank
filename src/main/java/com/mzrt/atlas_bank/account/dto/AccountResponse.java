package com.mzrt.atlas_bank.account.dto;

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
