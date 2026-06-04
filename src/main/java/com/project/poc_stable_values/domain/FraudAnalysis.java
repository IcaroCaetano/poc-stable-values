package com.project.poc_stable_values.domain;

import java.time.Instant;
import java.util.UUID;

public class FraudAnalysis {

    private final String analysisId;
    private final String cpf;
    private final double faceMatchScore;
    private final double livenessScore;
    private final FraudStatus status;
    private final Instant createdAt;

    public FraudAnalysis(String cpf,
            double faceMatchScore,
            double livenessScore) {

        this.analysisId = UUID.randomUUID().toString();
        this.cpf = cpf;
        this.faceMatchScore = faceMatchScore;
        this.livenessScore = livenessScore;
        this.status = determineStatus(faceMatchScore, livenessScore);
        this.createdAt = Instant.now();
    }

    private FraudStatus determineStatus(
            double faceMatchScore,
            double livenessScore) {

        if (faceMatchScore >= 90 && livenessScore >= 90) {
            return FraudStatus.APPROVED;
        }

        if (faceMatchScore >= 70 && livenessScore >= 70) {
            return FraudStatus.MANUAL_REVIEW;
        }

        return FraudStatus.REJECTED;
    }

    public String getAnalysisId() {

        return analysisId;
    }

    public String getCpf() {

        return cpf;
    }

    public double getFaceMatchScore() {

        return faceMatchScore;
    }

    public double getLivenessScore() {

        return livenessScore;
    }

    public FraudStatus getStatus() {

        return status;
    }

    public Instant getCreatedAt() {

        return createdAt;
    }

    @Override
    public String toString() {
        return """
                FraudAnalysis{
                    analysisId='%s',
                    cpf='%s',
                    faceMatchScore=%.2f,
                    livenessScore=%.2f,
                    status=%s,
                    createdAt=%s
                }
                """.formatted(
                analysisId,
                cpf,
                faceMatchScore,
                livenessScore,
                status,
                createdAt
        );
    }
}