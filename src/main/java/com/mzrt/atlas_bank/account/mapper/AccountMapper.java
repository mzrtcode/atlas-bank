package com.mzrt.atlas_bank.account.mapper;

import com.mzrt.atlas_bank.account.dto.AccountResponse;
import com.mzrt.atlas_bank.account.dto.CreateAccountRequest;
import com.mzrt.atlas_bank.account.model.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountResponse toResponse(Account account);
    Account toEntity(CreateAccountRequest accountRequest);
}
