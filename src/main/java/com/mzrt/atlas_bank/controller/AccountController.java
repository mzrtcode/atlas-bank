package com.mzrt.atlas_bank.controller;

import com.mzrt.atlas_bank.model.Account;
import com.mzrt.atlas_bank.model.Transaction;
import com.mzrt.atlas_bank.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Account create(@RequestBody Account account){
        return accountService.create(account);
    }

    @GetMapping
    public List<Account> findAll(){
        return accountService.findAll();
    }

    @GetMapping("/{id}")
    public Account findById(@PathVariable Long id){
        return accountService.findById(id);
    }

    @PostMapping("/transfer")
    @ResponseStatus(HttpStatus.OK)
    public Transaction transfer(@RequestParam Long fromId,
                                @RequestParam Long toId,
                                @RequestParam BigDecimal amount)
    {
        return accountService.transfer(fromId, toId, amount);
    }

    @GetMapping("/{id}/transactions")
    public List<Transaction> getTransactions(@PathVariable Long id){
        return accountService.getTransactions(id);
    }

}
