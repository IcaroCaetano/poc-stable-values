package com.project.poc_stable_values.api.response;


import com.project.poc_stable_values.domain.FraudAnalysis;
import com.project.poc_stable_values.domain.FraudStatus;

import java.time.Instant;

public record FraudAnalysisResult(
        String analysisId,
        String cpf,
        double faceMatchScore,
        double livenessScore,
        FraudStatus status,
        Instant createdAt
) {

    public static FraudAnalysisResult from(
            FraudAnalysis analysis) {

        return new FraudAnalysisResult(
                analysis.getAnalysisId(),
                analysis.getCpf(),
                analysis.getFaceMatchScore(),
                analysis.getLivenessScore(),
                analysis.getStatus(),
                analysis.getCreatedAt()
        );
    }
}