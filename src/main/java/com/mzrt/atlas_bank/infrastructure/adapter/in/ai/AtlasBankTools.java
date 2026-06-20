package com.mzrt.atlas_bank.infrastructure.adapter.in.ai;


import com.mzrt.atlas_bank.application.command.CloseAccountCommand;
import com.mzrt.atlas_bank.application.command.TransferMoneyCommand;
import com.mzrt.atlas_bank.application.port.in.CloseAccountUseCase;
import com.mzrt.atlas_bank.application.port.in.GetAccountUseCase;
import com.mzrt.atlas_bank.application.port.in.TransferMoneyUseCase;
import com.mzrt.atlas_bank.domain.exception.AccountNotActiveException;
import com.mzrt.atlas_bank.application.query.GetAccountStatementQuery;
import com.mzrt.atlas_bank.domain.exception.InsufficientFundsException;
import com.mzrt.atlas_bank.domain.model.account.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class AtlasBankTools {

    private final TransferMoneyUseCase transferMoneyUseCase;
    private final GetAccountUseCase getAccountUseCase;
    private final CloseAccountUseCase closeAccountUseCase;

    @Tool(
            description = """
        Realiza una transferencia de dinero entre dos cuentas internas de Atlas Bank.

        REGLAS IMPORTANTES:
        - SIEMPRE usar el ID interno de la cuenta, nunca el accountNumber.
        - El ID es numérico (ejemplo: 1, 2, 3).
        - El monto debe ser positivo.
        - Solo ejecutar cuando el usuario exprese intención clara de transferir dinero.
        """
    )
    public String transferMoney(
            @ToolParam(description = "ID numérico interno de la cuenta origen (NO accountNumber)") String sourceAccountId,

            @ToolParam(description = "ID numérico interno de la cuenta destino (NO accountNumber)") String targetAccountId,

            @ToolParam(description = "Monto exacto a transferir. Debe ser mayor a cero") BigDecimal amount
    ) {
        try {
            var command = TransferMoneyCommand.builder()
                    .fromId(Long.parseLong(sourceAccountId))
                    .toId(Long.parseLong(targetAccountId))
                    .amount(amount)
                    .build();

            transferMoneyUseCase.transfer(command);

            return """
                    Transferencia completada exitosamente.
                    Cuenta origen ID: %s
                    Cuenta destino ID: %s
                    Monto transferido: $%s
                    """.formatted(sourceAccountId, targetAccountId, amount);

        } catch (InsufficientFundsException e) {
            return "Transferencia rechazada: fondos insuficientes.";
        } catch (Exception e) {
            return "Error al procesar la transferencia: " + e.getMessage();
        }
    }

    @Tool(
            description = """
        Consulta el saldo actual de una cuenta bancaria de Atlas Bank.

        REGLAS IMPORTANTES:
        - SIEMPRE usar el ID interno de la cuenta.
        - NUNCA usar accountNumber.
        - El ID es numérico.
        """
    )
    public String getBalance(
            @ToolParam(description = "ID numérico interno de la cuenta (NO accountNumber)") String accountId
    ) {
        try {
            Account account = getAccountUseCase.findById(Long.parseLong(accountId));

            return """
                    Cuenta ID: %s
                    Titular: %s
                    Saldo disponible: $%s %s
                    Estado: %s
                    """.formatted(
                    account.getId(),
                    account.getOwnerName(),
                    account.getBalance().getAmount(),
                    account.getBalance().getCurrency(),
                    account.getStatus()
            );

        } catch (Exception e) {
            return "No se pudo consultar la cuenta: " + e.getMessage();
        }
    }

    @Tool(
            description = """
        Cierra una cuenta bancaria de Atlas Bank.

        REGLAS IMPORTANTES:
        - SIEMPRE usar el ID interno de la cuenta, nunca el accountNumber.
        - El ID es numérico (ejemplo: 1, 2, 3).
        - Solo cerrar cuando el usuario exprese intención clara de cerrar la cuenta.
        - La cuenta debe estar activa y tener saldo cero.
        """
    )
    public String closeAccount(
            @ToolParam(description = "ID numérico interno de la cuenta a cerrar (NO accountNumber)") String accountId
    ) {
        try {
            var command = new CloseAccountCommand(Long.parseLong(accountId));
            closeAccountUseCase.close(command);

            return """
                    Cuenta cerrada exitosamente.
                    Cuenta ID: %s
                    """.formatted(accountId);

        } catch (AccountNotActiveException e) {
            return "No se pudo cerrar la cuenta: " + e.getMessage();
        } catch (IllegalStateException e) {
            return "No se pudo cerrar la cuenta: " + e.getMessage();
        } catch (Exception e) {
            return "Error al cerrar la cuenta: " + e.getMessage();
        }
    }
}
