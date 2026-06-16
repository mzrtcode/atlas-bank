package com.mzrt.atlas_bank.infrastructure.adapter.in.rest;

import com.mzrt.atlas_bank.application.port.in.GetTransactionUseCase;
import com.mzrt.atlas_bank.application.port.in.TransferMoneyUseCase;
import com.mzrt.atlas_bank.infrastructure.adapter.in.rest.dto.TransactionResponse;
import com.mzrt.atlas_bank.infrastructure.adapter.in.rest.dto.TransferRequest;
import com.mzrt.atlas_bank.infrastructure.adapter.in.rest.dto.TransactionMapper;
import com.mzrt.atlas_bank.domain.model.transaction.Transaction;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final GetTransactionUseCase transactionQueryService;
    private final TransferMoneyUseCase transferMoneyUseCase;
    private final TransactionMapper transactionMapper;

    @PostMapping("/transfer")
    @ResponseStatus(HttpStatus.OK)
    public TransactionResponse transfer(@Valid @RequestBody TransferRequest request)
    {
        Transaction transaction = transferMoneyUseCase.execute(
                request.fromAccountId(),
                request.toAccountId(),
                request.amount());
        return  transactionMapper.toResponse(transaction);
    }

    @GetMapping("/{id}/transactions")
    public List<TransactionResponse> getTransactions(@PathVariable Long id){
        return transactionQueryService.getByAccountId(id).stream()
                .map(transactionMapper::toResponse)
                .toList();
    }
}
