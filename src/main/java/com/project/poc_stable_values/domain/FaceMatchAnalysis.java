package com.project.poc_stable_values.domain;

import java.time.Instant;

public class FaceMatchAnalysis extends FraudAnalysis {

    private final double faceMatchScore;

    private FaceMatchAnalysis(
            String analysisId,
            String cpf,
            String personName,
            String source,
            Instant createdAt,
            double faceMatchScore) {

        super(
                analysisId,
                cpf,
                personName,
                source,
                createdAt);

        this.faceMatchScore = faceMatchScore;
    }

    public FaceMatchAnalysis(
            String cpf,
            String personName,
            String source,
            String faceMatchScore) {

        cpf = cpf.replaceAll("\\D", "");

        personName = personName.trim();

        source = source.toUpperCase();

        double score = Double.parseDouble(faceMatchScore);

        if (score < 0 || score > 100) {
            throw new IllegalArgumentException(
                    "Face Match Score inválido");
        }

        this(
                java.util.UUID.randomUUID().toString(),
                cpf,
                personName,
                source,
                Instant.now(),
                score
        );
    }

    public double getFaceMatchScore() {
        return faceMatchScore;
    }
}