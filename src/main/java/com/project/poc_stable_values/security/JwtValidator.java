package com.project.poc_stable_values.security;

public class JwtValidator {

    public boolean validate(String token, JwtPublicKey publicKey) {

        System.out.println("Validating JWT using key " + publicKey.keyId() + " Token " + token );

        return true;
    }
}