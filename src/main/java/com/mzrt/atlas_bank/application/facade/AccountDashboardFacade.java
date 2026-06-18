package com.mzrt.atlas_bank.application.facade;

import com.mzrt.atlas_bank.application.port.in.GetAccountUseCase;
import com.mzrt.atlas_bank.application.port.in.GetTransactionsByAccountUseCase;
import com.mzrt.atlas_bank.application.query.DashboardReadModel;
import com.mzrt.atlas_bank.application.query.GetAccountStatementQuery;
import com.mzrt.atlas_bank.application.query.TransactionReadModel;
import com.mzrt.atlas_bank.domain.model.account.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountDashboardFacade {
    private final GetAccountUseCase accountService;
    private final GetTransactionsByAccountUseCase transactionQueryService;


    public DashboardReadModel getDashboard(Long accountId){
        Account account = accountService.findById(accountId);
        GetAccountStatementQuery query = new GetAccountStatementQuery(accountId);

        List<TransactionReadModel> transactions = transactionQueryService
                .getByAccountId(query);

        return DashboardReadModel.builder()
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
