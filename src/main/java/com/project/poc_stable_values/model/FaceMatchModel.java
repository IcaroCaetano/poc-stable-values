package com.project.poc_stable_values.model;

public class FaceMatchModel {

    private final String version;

    public FaceMatchModel(String version) {

        this.version = version;
    }

    public double analyze(String selfieBase64, String documentBase64) {

        return Math.random() * 100;
    }

    public String version() {

        return version;
    }
}