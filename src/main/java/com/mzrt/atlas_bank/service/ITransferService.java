package com.mzrt.atlas_bank.service;

import com.mzrt.atlas_bank.model.Transaction;

import java.math.BigDecimal;

public interface ITransferService {
    Transaction execute(Long fromId, Long toId, BigDecimal amount);
}
