package com.mzrt.atlas_bank.transaction.fraud;

import com.mzrt.atlas_bank.application.port.out.FraudCheckPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Slf4j
public class ExternalFraudCheckAdapter implements FraudChecker, FraudCheckPort {
    @Override
    public FraudCheckResult check(Long accountId, BigDecimal amount) {
        ExternalFraudResponseDTO response = callExternalApi(accountId, amount);
        log.info("Respuesta del servicio de fraude - riesgo: {}, score:{}, recomendacion: {}",
                response.getRiskLevel(), response.getScore(), response.getRecommendation()
                );
        if("BLOCK".equals(response.getRecommendation())){
            return FraudCheckResult.blocked(
                    "Operacion bloquedada por riesgo " + response.getRiskLevel()
                    + " (score: " + response.getScore() + ")"
            );
        }
        return FraudCheckResult.allowed();
    }

    private ExternalFraudResponseDTO callExternalApi(Long accountId, BigDecimal amount) {

        if(amount.compareTo(new BigDecimal("1000000")) > 0){
            return new ExternalFraudResponseDTO("HIGH", 0.95, "BLOCK");
        }
        return new ExternalFraudResponseDTO("LOW", 0.1, "ALLOW");
    }


}
