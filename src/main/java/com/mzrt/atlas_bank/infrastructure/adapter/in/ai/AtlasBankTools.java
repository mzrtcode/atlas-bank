package com.mzrt.atlas_bank.infrastructure.adapter.in.ai;


import com.mzrt.atlas_bank.application.command.TransferMoneyCommand;
import com.mzrt.atlas_bank.application.port.in.GetAccountUseCase;
import com.mzrt.atlas_bank.application.port.in.TransferMoneyUseCase;
import com.mzrt.atlas_bank.domain.exception.InsufficientFundsException;
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

    @Tool(description = "Tranferir dinero entra dos cuentas del banco atlas-bank")
    public String transferMoney(
        @ToolParam(description = "ID de la cuenta origen") String sourceAccountId,
        @ToolParam(description = "ID de la cuenta destino") String targetAccountId,
        @ToolParam(description = "Monto a tranferir") BigDecimal amount
    ){
        try{
            var command  = TransferMoneyCommand.builder()
                    .fromId(Long.parseLong(sourceAccountId))
                    .toId(Long.parseLong(targetAccountId))
                    .amount(amount)
                    .build();

            transferMoneyUseCase.transfer(command);
            return "Tranferencia realizada con exito. Monto: $" + amount;

        }catch(InsufficientFundsException e){
            return "No se pudo realizar la transferencia: " + e.getMessage();

        }catch(Exception e){

            return "No se pudo realizar la transferencia: " + e.getMessage();
        }
        
    }
}
