package com.project.poc_stable_values.dto;

public record FraudAnalysisRequest(
        String cpf,
        String selfieBase64,
        String documentBase64
) {
}