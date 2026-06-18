package com.mzrt.atlas_bank.application.port.in;

import com.mzrt.atlas_bank.application.query.DashboardReadModel;
import com.mzrt.atlas_bank.application.query.GetAccountStatementQuery;
import com.mzrt.atlas_bank.application.query.TransactionReadModel;

import java.util.List;

public interface GetTransactionsByAccountUseCase {
    List<TransactionReadModel> getByAccountId(GetAccountStatementQuery query);

}
