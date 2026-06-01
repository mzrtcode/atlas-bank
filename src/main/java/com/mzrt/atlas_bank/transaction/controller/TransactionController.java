package com.mzrt.atlas_bank.transaction.controller;

import com.mzrt.atlas_bank.transaction.model.Transaction;
import com.mzrt.atlas_bank.transaction.service.ITransactionQueryService;
import com.mzrt.atlas_bank.transaction.service.ITransferService;
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

    @PostMapping("/transfer")
    @ResponseStatus(HttpStatus.OK)
    public Transaction transfer(@RequestParam Long fromId,
                                @RequestParam Long toId,
                                @RequestParam BigDecimal amount)
    {
        return transferService.execute(fromId, toId, amount);
    }

    @GetMapping("/{id}/transactions")
    public List<Transaction> getTransactions(@PathVariable Long id){
        return transactionQueryService.getByAccountId(id);
    }
}
