package com.objectcomputing.todo.security;

import io.micronaut.http.HttpRequest;
import io.micronaut.security.oauth2.client.IdTokenClaimsValidator;
import io.micronaut.security.oauth2.configuration.OauthClientConfiguration;
import io.micronaut.security.token.jwt.generator.claims.JwtClaims;
import io.micronaut.security.token.jwt.validator.GenericJwtClaimsValidator;

import java.util.Collection;

public class Hd implements GenericJwtClaimsValidator {


    @Override
    public boolean validate(JwtClaims claims, HttpRequest<?> request) {
        return false;
    }
}
