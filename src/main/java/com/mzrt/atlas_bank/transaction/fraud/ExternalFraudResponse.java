package com.mzrt.atlas_bank.transaction.fraud;

public class ExternalFraudResponse {
    private String riskLevel; //LOW, MEDIUM, HIGH
    private String score; //0.0 a 1.0
    private String recommendation; //ALLOW, REVIEW, BLOCK
}
