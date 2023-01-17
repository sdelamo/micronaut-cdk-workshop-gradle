package com.objectcomputing.todo;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.bind.ArgumentBinder;
import io.micronaut.core.convert.ArgumentConversionContext;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.bind.binders.TypedRequestArgumentBinder;

import java.security.Principal;
import java.util.Optional;

public class PrincipalArgumentBinderReplacement implements TypedRequestArgumentBinder<Principal> {

    @NonNull
    private final String username;
    public PrincipalArgumentBinderReplacement(@NonNull String username) {
        this.username = username;
    }

    @Override
    public Argument<Principal> argumentType() {
        return Argument.of(Principal.class);
    }

    @Override
    public ArgumentBinder.BindingResult<Principal> bind(ArgumentConversionContext<Principal> context, HttpRequest<?> source) {
        return () -> Optional.of(() -> username);
    }
}
