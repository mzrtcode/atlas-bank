package com.mzrt.atlas_bank.transaction.fraud;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ExternalFraudResponse {
    private String riskLevel; //LOW, MEDIUM, HIGH
    private double score; //0.0 a 1.0
    private String recommendation; //ALLOW, REVIEW, BLOCK
}
