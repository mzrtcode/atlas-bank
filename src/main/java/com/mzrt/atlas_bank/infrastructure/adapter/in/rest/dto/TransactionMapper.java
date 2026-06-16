package com.mzrt.atlas_bank.infrastructure.adapter.in.rest.dto;

import com.mzrt.atlas_bank.domain.model.transaction.Transaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    TransactionResponse toResponse(Transaction transaction);
}
