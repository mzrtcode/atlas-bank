package com.mzrt.atlas_bank.transaction.service.transfer;

import com.mzrt.atlas_bank.account.exception.AccountNotFoundException;
import com.mzrt.atlas_bank.account.model.Account;
import com.mzrt.atlas_bank.account.repository.DomainAccountRepository;
import com.mzrt.atlas_bank.transaction.model.Transaction;
import com.mzrt.atlas_bank.transaction.repository.TransactionDomainRepository;
import com.mzrt.atlas_bank.transaction.repository.TransactionRepository;
import com.mzrt.atlas_bank.transaction.service.domain.TransferDomainService;
import com.mzrt.atlas_bank.transaction.service.fee.FeeCalculator;
import com.mzrt.atlas_bank.transaction.service.factory.TransactionFactory;
import com.mzrt.atlas_bank.transaction.validation.chain.TransferValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransferService extends TransactionProcessor<TransferContext> implements ITransferService {

    private final DomainAccountRepository accountRepository;
    private final List<FeeCalculator> feeCalculators;
    private final List<TransferValidator> validators;
    private final TransferDomainService transferDomainService;

    public TransferService(TransactionDomainRepository transactionRepository,
                           DomainAccountRepository accountRepository,
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
    public Transaction execute(Long fromId, Long toId, BigDecimal amount) {
        // Buscar cuentas
        Account from = accountRepository.findById(fromId)
                .orElseThrow(() -> new AccountNotFoundException(fromId));
        Account to = accountRepository.findById(toId)
                .orElseThrow(() -> new AccountNotFoundException(toId));

        Transaction transaction = process(new TransferContext(from, to, amount));
        transaction.advancedTo(transaction.getState().validate());
        transaction.advancedTo(transaction.getState().execute());
        transaction.maskAsExecuted();
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
