package com.mzrt.atlas_bank.account.controller;

import com.mzrt.atlas_bank.account.model.Account;
import com.mzrt.atlas_bank.account.service.IAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final IAccountService accountService;

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

}
