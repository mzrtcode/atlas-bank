package com.mzrt.atlas_bank.account.facade;

import com.mzrt.atlas_bank.account.dto.DashboardResponse;
import com.mzrt.atlas_bank.account.mapper.AccountMapper;
import com.mzrt.atlas_bank.account.model.Account;
import com.mzrt.atlas_bank.account.service.IAccountService;
import com.mzrt.atlas_bank.transaction.dto.TransactionResponse;
import com.mzrt.atlas_bank.transaction.fraud.FraudCheckResult;
import com.mzrt.atlas_bank.transaction.fraud.FraudChecker;
import com.mzrt.atlas_bank.transaction.mapper.TransactionMapper;
import com.mzrt.atlas_bank.transaction.service.ITransactionQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountDashboardFacade {
    private final IAccountService accountService;
    private final ITransactionQueryService transactionQueryService;
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
                .balance(account.getBalance())
                .status(account.getStatus().name())
                .recentTransactions(transactions)
                .build();
    }
}
