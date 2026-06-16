package com.mzrt.atlas_bank.infrastructure.adapter.in.rest;

import com.mzrt.atlas_bank.application.port.in.GetAccountUseCase;
import com.mzrt.atlas_bank.application.port.in.GetTransactionUseCase;
import com.mzrt.atlas_bank.infrastructure.adapter.in.rest.dto.DashboardResponse;
import com.mzrt.atlas_bank.domain.model.account.Account;
import com.mzrt.atlas_bank.infrastructure.adapter.in.rest.dto.TransactionResponse;
import com.mzrt.atlas_bank.infrastructure.adapter.in.rest.dto.TransactionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountDashboardFacade {
    private final GetAccountUseCase accountService;
    private final GetTransactionUseCase transactionQueryService;
    private final TransactionMapper transactionMapper;


    public DashboardResponse getDashboard(Long accountId){
        Account account = accountService.findById(accountId);
        List<TransactionResponse> transactions = transactionQueryService
                .getByAccountId(accountId)
                .stream().map(transactionMapper::toResponse)
                .toList();

        return DashboardResponse.builder()
                .accountId(accountId)
                .accountNumber(account.getAccountNumber())
                .ownerName(account.getOwnerName())
                .type(account.getType().name())
                .balance(account.getBalance().getAmount())
                .status(account.getStatus().name())
                .recentTransactions(transactions)
                .build();
    }
}
