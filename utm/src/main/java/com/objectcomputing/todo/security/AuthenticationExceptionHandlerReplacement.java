package com.objectcomputing.todo.security;

import io.micronaut.context.annotation.Replaces;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import io.micronaut.security.authentication.AuthenticationException;
import io.micronaut.security.authentication.AuthenticationExceptionHandler;
import jakarta.inject.Singleton;

import java.net.URI;

@Replaces(AuthenticationExceptionHandler.class)
@Singleton
public class AuthenticationExceptionHandlerReplacement implements ExceptionHandler<AuthenticationException, MutableHttpResponse<?>> {

    public static final String LOGIN_FAILED = "/login-failed.html";

    @Override
    public MutableHttpResponse<?> handle(HttpRequest request, AuthenticationException exception) {
        return HttpResponse.seeOther(URI.create(LOGIN_FAILED));
    }
}
