package com.mzrt.atlas_bank.transaction.service.transfer;

import com.mzrt.atlas_bank.account.exception.AccountNotFoundException;
import com.mzrt.atlas_bank.account.model.Account;
import com.mzrt.atlas_bank.account.model.AccountStatus;
import com.mzrt.atlas_bank.transaction.exception.AccountNotActiveException;
import com.mzrt.atlas_bank.transaction.exception.InsufficientFundsException;
import com.mzrt.atlas_bank.transaction.fraud.FraudCheckResult;
import com.mzrt.atlas_bank.transaction.fraud.FraudChecker;
import com.mzrt.atlas_bank.transaction.model.Transaction;
import com.mzrt.atlas_bank.account.repository.AccountRepository;
import com.mzrt.atlas_bank.transaction.repository.TransactionRepository;
import com.mzrt.atlas_bank.transaction.service.event.TransactionExecutedEvent;
import com.mzrt.atlas_bank.transaction.service.exception.FraudCheckException;
import com.mzrt.atlas_bank.transaction.service.fee.FeeCalculator;
import com.mzrt.atlas_bank.transaction.service.factory.TransactionFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransferService extends TransactionProcessor<TransferContext> implements ITransferService {

    private final AccountRepository accountRepository;
    private final List<FeeCalculator> feeCalculators;
    private final ApplicationEventPublisher publisher;
    private final FraudChecker fraudChecker;

    public TransferService(TransactionRepository transactionRepository, AccountRepository accountRepository, List<FeeCalculator> feeCalculators, ApplicationEventPublisher publisher, FraudChecker fraudChecker) {
        super(transactionRepository);
        this.accountRepository = accountRepository;
        this.feeCalculators = feeCalculators;
        this.publisher = publisher;
        this.fraudChecker = fraudChecker;
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

        publisher.publishEvent(new TransactionExecutedEvent(
                transaction.getId(),
                transaction.getType(),
                transaction.getSourceAccountId(),
                transaction.getTargetAccountId(),
                transaction.getAmount(),
                transaction.getFee()
        ));

        return transaction;

    }

    @Override
    protected void validate(TransferContext context) {

        if (context.accountFrom().getStatus() != AccountStatus.ACTIVE) {
            throw new AccountNotActiveException(context.accountFrom().getId(), context.accountTo().getStatus().name());
        }
        if (context.accountTo().getStatus() != AccountStatus.ACTIVE) {
            throw new AccountNotActiveException(context.accountTo().getId(), context.accountTo().getStatus().name());
        }


        if (context.accountFrom().getBalance().compareTo(context.amount()) < 0) {
            throw new InsufficientFundsException(context.accountFrom().getId(), context.accountFrom().getBalance(), context.amount());
        }


        FraudCheckResult checkResult = fraudChecker.check(context.accountFrom().getId(), context.amount());

        if (checkResult.blocked()) throw new FraudCheckException(checkResult.reason());
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

        context.accountFrom().setBalance(context.accountFrom().getBalance().subtract(context.amount()).subtract(fee));
        context.accountTo().setBalance(context.accountTo().getBalance().add(context.amount()));
        accountRepository.save(context.accountFrom());
        accountRepository.save(context.accountTo());

    }

    @Override
    protected Transaction save(TransferContext context, BigDecimal fee) {
        Transaction transaction = TransactionFactory.createTransfer(context, fee);
        return transactionRepository.save(transaction);
    }
}
