package com.mzrt.atlas_bank.transaction.controller;

import com.mzrt.atlas_bank.transaction.dto.TransactionResponse;
import com.mzrt.atlas_bank.transaction.dto.TransferRequest;
import com.mzrt.atlas_bank.transaction.mapper.TransactionMapper;
import com.mzrt.atlas_bank.transaction.model.Transaction;
import com.mzrt.atlas_bank.transaction.service.ITransactionQueryService;
import com.mzrt.atlas_bank.transaction.service.ITransferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final ITransactionQueryService transactionQueryService;
    private final ITransferService transferService;
    private final TransactionMapper transactionMapper;

    @PostMapping("/transfer")
    @ResponseStatus(HttpStatus.OK)
    public TransactionResponse transfer(@Valid @RequestBody TransferRequest request)
    {
        Transaction transaction = transferService.execute(
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
