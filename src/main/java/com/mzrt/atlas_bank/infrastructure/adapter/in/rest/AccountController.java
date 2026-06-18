package com.mzrt.atlas_bank.infrastructure.adapter.in.rest;

import com.mzrt.atlas_bank.application.facade.AccountDashboardFacade;
import com.mzrt.atlas_bank.application.port.in.CreateAccountUseCase;
import com.mzrt.atlas_bank.application.port.in.GetAccountUseCase;
import com.mzrt.atlas_bank.application.port.in.ListAccountUseCase;
import com.mzrt.atlas_bank.application.query.DashboardReadModel;
import com.mzrt.atlas_bank.application.query.TransactionReadModel;
import com.mzrt.atlas_bank.infrastructure.adapter.in.rest.dto.AccountResponse;
import com.mzrt.atlas_bank.infrastructure.adapter.in.rest.dto.CreateAccountRequest;
import com.mzrt.atlas_bank.infrastructure.adapter.in.rest.dto.DashboardResponse;
import com.mzrt.atlas_bank.domain.model.account.Account;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final ListAccountUseCase listAccountUseCase;
    private final CreateAccountUseCase createAccountUseCase;
    private final AccountMapper accountMapper;
    private final GetAccountUseCase getAccountUseCase;
    private final AccountDashboardFacade dashboardFacade;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponse create(@Valid @RequestBody CreateAccountRequest request){
        Account account = accountMapper.toEntity(request);
        return accountMapper.toResponse(createAccountUseCase.create(account));
    }

    @GetMapping
    public List<AccountResponse> findAll(){
        return listAccountUseCase.findAll().stream()
                .map(accountMapper::toResponse)
                .toList();

    }

    @GetMapping("/{id}")
    public AccountResponse findById(@PathVariable Long id){
        return accountMapper.toResponse(getAccountUseCase.findById(id));
    }

    @GetMapping("/{id}/dashboard")
    @ResponseStatus(HttpStatus.OK)
    public DashboardReadModel getDashboard(@PathVariable Long id){
        return dashboardFacade.getDashboard(id);
    }

}
