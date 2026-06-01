package com.mzrt.atlas_bank.account.dto;

import java.math.BigDecimal;

public record CreateAccountRequest(
        String accountNumber,
        String ownerName,
        String email,
        String type,
        BigDecimal balance
) { }
