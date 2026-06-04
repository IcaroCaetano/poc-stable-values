package com.project.poc_stable_values.engine;

import com.project.poc_stable_values.security.JwtPublicKey;

public class FraudEngine {

    private final StableValue<FaceMatchModel> faceMatchModel = StableValue.of();

    private final StableValue<LivenessModel> livenessModel = StableValue.of();

    private final StableValue<JwtPublicKey> jwtPublicKey = StableValue.of();

    public FaceMatchModel faceMatchModel() {

        return faceMatchModel.orElseSet(this::loadFaceMatchModel);
    }

    public LivenessModel livenessModel() {

        return livenessModel.orElseSet(this::loadLivenessModel);
    }

    public JwtPublicKey jwtPublicKey() {

        return jwtPublicKey.orElseSet(this::loadJwtPublicKey);
    }

    private FaceMatchModel loadFaceMatchModel() {

        System.out.println("Loading Face Match Model...");

        sleep();

        return new FaceMatchModel("face-v3");
    }

    private LivenessModel loadLivenessModel() {

        System.out.println("Loading Liveness Model...");

        sleep();

        return new LivenessModel("liveness-v2");
    }

    private JwtPublicKey loadJwtPublicKey() {

        System.out.println(
                "Loading JWT Public Key..."
        );

        sleep();

        return new JwtPublicKey(
                "kid-001",
                "PUBLIC_KEY_CONTENT"
        );
    }

    private void sleep() {

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}