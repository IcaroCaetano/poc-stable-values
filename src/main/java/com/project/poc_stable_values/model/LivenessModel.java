package com.project.poc_stable_values.model;

public class LivenessModel {

    private final String version;

    public LivenessModel(String version) {

        this.version = version;
    }

    public double analyze(String selfieBase64) {

        return Math.random() * 100;
    }

    public String version() {

        return version;
    }
}