package com.mzrt.atlas_bank.transaction.fraud;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExternalFraudResponseDTO {
    private String riskLevel; //LOW, MEDIUM, HIGH
    private double score; //0.0 a 1.0
    private String recommendation; //ALLOW, REVIEW, BLOCK
}
