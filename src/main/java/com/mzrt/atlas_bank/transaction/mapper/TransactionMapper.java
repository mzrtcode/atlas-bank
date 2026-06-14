package com.mzrt.atlas_bank.transaction.mapper;

import com.mzrt.atlas_bank.transaction.dto.TransactionResponse;
import com.mzrt.atlas_bank.domain.model.transaction.Transaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    TransactionResponse toResponse(Transaction transaction);
}
