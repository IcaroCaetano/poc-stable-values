package com.project.poc_stable_values.service;

import com.project.poc_stable_values.domain.FraudAnalysis;
import com.project.poc_stable_values.dto.FraudAnalysisRequest;
import com.project.poc_stable_values.engine.FraudEngine;
import com.project.poc_stable_values.security.JwtValidator;

public class FraudAnalysisService {

    private final FraudEngine fraudEngine = new FraudEngine();
    private final JwtValidator jwtValidator = new JwtValidator();

    public FraudAnalysis analyze(FraudAnalysisRequest request) {

        var faceMatchModel = fraudEngine.faceMatchModel();

        var livenessModel = fraudEngine.livenessModel();

        var publicKey = fraudEngine.jwtPublicKey();

        jwtValidator.validate(request.jwtToken(), publicKey);

        double faceMatchScore = faceMatchModel.analyze(request.selfieBase64(), request.documentBase64());

        double livenessScore = livenessModel.analyze(request.selfieBase64());

        return new FraudAnalysis(request.cpf(),faceMatchScore, livenessScore);
    }
}