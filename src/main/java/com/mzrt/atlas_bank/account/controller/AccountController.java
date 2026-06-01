package com.mzrt.atlas_bank.account.controller;

import com.mzrt.atlas_bank.account.dto.AccountResponse;
import com.mzrt.atlas_bank.account.mapper.AccountMapper;
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
    private final AccountMapper accountMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponse create(@RequestBody Account account){
        return accountMapper.toResponse(accountService.create(account));
    }

    @GetMapping
    public List<AccountResponse> findAll(){
        return accountService.findAll().stream()
                .map(accountMapper::toResponse)
                .toList();

    }

    @GetMapping("/{id}")
    public AccountResponse findById(@PathVariable Long id){
        return accountMapper.toResponse(accountService.findById(id));
    }

}
