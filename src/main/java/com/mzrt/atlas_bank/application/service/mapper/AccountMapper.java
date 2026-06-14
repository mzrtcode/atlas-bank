package com.mzrt.atlas_bank.application.service.mapper;

import com.mzrt.atlas_bank.infrastructure.adapter.in.rest.dto.AccountResponse;
import com.mzrt.atlas_bank.infrastructure.adapter.in.rest.dto.CreateAccountRequest;
import com.mzrt.atlas_bank.domain.model.account.Account;
import com.mzrt.atlas_bank.domain.model.shared.Currency;
import com.mzrt.atlas_bank.domain.model.shared.Email;
import com.mzrt.atlas_bank.domain.model.shared.Money;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    @Mapping(target = "balance", source = "balance", qualifiedByName = "toAmount")
    @Mapping(target = "email", source = "email", qualifiedByName = "fromEmail")
    AccountResponse toResponse(Account account);

    @Mapping(target = "balance", source = "balance", qualifiedByName = "toMoney")
    @Mapping(target = "email", source = "email", qualifiedByName = "toEmail")
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

    @Named("toEmail")
    default Email toEmail(String email){
        if(email == null) return null;
        return Email.of(email);
    }

    @Named("fromEmail")
    default String fromEmail(Email email){
        if(email == null) return null;
        return email.getValue();
    }
}
