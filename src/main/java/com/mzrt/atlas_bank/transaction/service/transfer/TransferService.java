package com.mzrt.atlas_bank.transaction.service;

import com.mzrt.atlas_bank.account.exception.AccountNotFoundException;
import com.mzrt.atlas_bank.account.model.Account;
import com.mzrt.atlas_bank.transaction.exception.AccountNotActiveException;
import com.mzrt.atlas_bank.transaction.exception.InsufficientFundsException;
import com.mzrt.atlas_bank.transaction.model.Transaction;
import com.mzrt.atlas_bank.account.repository.AccountRepository;
import com.mzrt.atlas_bank.transaction.repository.TransactionRepository;
import com.mzrt.atlas_bank.account.service.fee.FeeCalculator;
import com.mzrt.atlas_bank.transaction.service.transfer.ITransferService;
import com.mzrt.atlas_bank.transaction.service.transfer.TransferContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransferService extends TransactionProcessor<TransferContext> implements ITransferService {

    private final AccountRepository accountRepository;
    private final List<FeeCalculator> feeCalculators;

    public TransferService(TransactionRepository transactionRepository, AccountRepository accountRepository, List<FeeCalculator> feeCalculators) {
        super(transactionRepository);
        this.accountRepository = accountRepository;
        this.feeCalculators = feeCalculators;
    }

    @Transactional
    @Override
    public Transaction execute(Long fromId, Long toId, BigDecimal amount) {
        // Buscar cuentas
        Account from = accountRepository.findById(fromId)
                .orElseThrow(() -> new AccountNotFoundException(fromId));
        Account to = accountRepository.findById(toId)
                .orElseThrow(() -> new AccountNotFoundException(toId));

        return process(new TransferContext(from, to, amount));

    }

    @Override
    protected void validate(TransferContext context) {

        if (!"ACTIVE".equals(context.accountFrom().getStatus())) {
            throw new AccountNotActiveException(context.accountFrom().getId(), context.accountFrom().getStatus());
        }
        if (!"ACTIVE".equals(context.accountTo().getStatus())) {
            throw new AccountNotActiveException(context.accountTo().getId(), context.accountTo().getStatus());
        }


        if (context.accountFrom().getBalance().compareTo(context.amount()) < 0) {
            throw new InsufficientFundsException(context.accountFrom().getId(), context.accountFrom().getBalance(), context.amount());
        }
    }

    @Override
    protected BigDecimal calculateFee(TransferContext context) {

        return feeCalculators.stream()
                .filter(fc -> fc.supports(context.accountFrom().getType()))
                .findFirst()
                .orElseThrow(() ->  new RuntimeException("No hay calculador para el tipo de cuenta " + context.accountFrom().getType()))
                .calculate(context.amount());

    }

    @Override
    protected void execute(TransferContext context, BigDecimal fee) {

        context.accountFrom().setBalance(context.accountFrom().getBalance().subtract(context.amount()).subtract(fee));
        context.accountTo().setBalance(context.accountTo().getBalance().add(context.amount()));
        accountRepository.save(context.accountFrom());
        accountRepository.save(context.accountTo());

    }

    @Override
    protected Transaction save(TransferContext context, BigDecimal fee) {

        Transaction transaction = new Transaction();
        transaction.setType("TRANSFER");
        transaction.setSourceAccountId(context.accountFrom().getId());
        transaction.setTargetAccountId(context.accountTo().getId());
        transaction.setAmount(context.amount());
        transaction.setFee(fee);
        transaction.setStatus("EXECUTED");

        return transactionRepository.save(transaction);
    }
}
