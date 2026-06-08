package com.mzrt.atlas_bank.account.mapper;

import com.mzrt.atlas_bank.account.dto.AccountResponse;
import com.mzrt.atlas_bank.account.dto.CreateAccountRequest;
import com.mzrt.atlas_bank.account.model.Account;
import com.mzrt.atlas_bank.shared.model.Currency;
import com.mzrt.atlas_bank.shared.model.Money;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.awt.*;
import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    @Mapping(target = "balance", source = "balance", qualifiedByName = "toAmount")
    AccountResponse toResponse(Account account);

    @Mapping(target = "balance", source = "balance", qualifiedByName = "toMoney")
    Account toEntity(CreateAccountRequest accountRequest);

    @Named("toMoney")
    default Money toMoney(BigDecimal amount){
        if(amount == null) return null;
        return Money.of(amount, Currency.COP);
    }

    @Named("toAmount")
    default BigDecimal toAmount(Money money){
        if(money == null) return null;
        return money.getAmount();
    }
}
