package com.mzrt.atlas_bank.infrastructure.adapter.in.rest.dto;

import com.mzrt.atlas_bank.transaction.dto.TransactionResponse;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record DashboardResponse(
        Long accountId,
        String accountNumber,
        String ownerName,
        String type,
        BigDecimal balance,
        String status,
        List<TransactionResponse> recentTransactions,
        boolean fraudBlocked,
        String fraudReason

) { }
