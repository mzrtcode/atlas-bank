package com.mzrt.atlas_bank.transaction.mapper;

import com.mzrt.atlas_bank.transaction.dto.TransactionResponse;
import com.mzrt.atlas_bank.transaction.model.Transaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    TransactionResponse toResponse(Transaction transaction);
}
