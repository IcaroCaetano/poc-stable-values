package com.project.poc_stable_values.security;

public record JwtPublicKey(
        String keyId,
        String publicKey
) { }