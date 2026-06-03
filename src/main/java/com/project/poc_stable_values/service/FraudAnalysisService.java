package com.project.poc_stable_values.service;

import com.project.poc_stable_values.dto.FraudAnalysisRequest;

public class FraudAnalysisService {

    public void analyze(FraudAnalysisRequest request) {

        var faceMatch =
                new FaceMatchAnalysis(
                        request.cpf(),
                        request.personName(),
                        request.source(),
                        request.faceMatchScore());

        var liveness =
                new LivenessAnalysis(
                        request.cpf(),
                        request.personName(),
                        request.source(),
                        request.livenessScore());

        System.out.println(
                "Face Match Score: "
                        + faceMatch.getFaceMatchScore());

        System.out.println(
                "Liveness Score: "
                        + liveness.getLivenessScore());
    }
}