package com.mzrt.atlas_bank.application.service;

import com.mzrt.atlas_bank.application.command.TransferMoneyCommand;
import com.mzrt.atlas_bank.application.port.out.TransactionRepositoryPort;
import com.mzrt.atlas_bank.domain.exception.AccountNotFoundException;
import com.mzrt.atlas_bank.domain.model.account.Account;
import com.mzrt.atlas_bank.application.port.in.TransferMoneyUseCase;
import com.mzrt.atlas_bank.application.port.out.AccountRepositoryPort;
import com.mzrt.atlas_bank.domain.model.transaction.TransferContext;
import com.mzrt.atlas_bank.domain.model.transaction.Transaction;
import com.mzrt.atlas_bank.domain.service.TransferDomainService;
import com.mzrt.atlas_bank.domain.strategy.fee.FeeCalculator;
import com.mzrt.atlas_bank.domain.validation.TransferValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransferService extends TransactionProcessor<TransferContext> implements TransferMoneyUseCase {

    private final AccountRepositoryPort accountRepository;
    private final List<FeeCalculator> feeCalculators;
    private final List<TransferValidator> validators;
    private final TransferDomainService transferDomainService;

    public TransferService(TransactionRepositoryPort transactionRepository,
                           AccountRepositoryPort accountRepository,
                           List<FeeCalculator> feeCalculators,
                           List<TransferValidator> validators,
                           TransferDomainService transferDomainService) {
        super(transactionRepository);
        this.accountRepository = accountRepository;
        this.feeCalculators = feeCalculators;
        this.validators = validators;
        this.transferDomainService = transferDomainService;
    }

    @Transactional
    @Override
    public Transaction transfer(TransferMoneyCommand command) {
        // Buscar cuentas
        Account from = accountRepository.findById(command.fromId())
                .orElseThrow(() -> new AccountNotFoundException(command.fromId()));
        Account to = accountRepository.findById(command.toId())
                .orElseThrow(() -> new AccountNotFoundException(command.toId()));

        Transaction transaction = process(new TransferContext(from, to, command.amount()));
        transaction.executeTransfer();
        transactionRepository.save(transaction);


        return transaction;

    }

    @Override
    protected void validate(TransferContext context) {

        validators.forEach(validator -> validator.validate(context));
    }

    @Override
    protected BigDecimal calculateFee(TransferContext context) {

        return feeCalculators.stream()
                .filter(fc -> fc.supports(context.accountFrom().getType()))
                .findFirst()
                .orElseThrow(() ->  new RuntimeException("No hay calculador para el tipo de cuenta " + context.accountFrom().getStatus().name()))
                .calculate(context.amount());
    }

    @Override
    protected void execute(TransferContext context, BigDecimal fee) {

        transferDomainService.transfer(
                context.accountFrom(),
                context.accountTo(),
                context.amount(),
                fee
        );

        accountRepository.save(context.accountFrom());
        accountRepository.save(context.accountTo());

    }

    @Override
    protected Transaction save(TransferContext context, BigDecimal fee) {
        Transaction transaction = TransactionFactory.createTransfer(context, fee);
        return transactionRepository.save(transaction);
    }
}
