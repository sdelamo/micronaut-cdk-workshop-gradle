package com.example.core;

import io.micronaut.core.annotation.NonNull;
import java.util.Optional;
import jakarta.inject.Singleton;

@Singleton
public class DefaultRuntimeProvider implements RuntimeProvider {
    @NonNull
    public Optional<String> runtime() {
        return Optional.ofNullable(System.getenv("AWS_EXECUTION_ENV"));
    }
}
