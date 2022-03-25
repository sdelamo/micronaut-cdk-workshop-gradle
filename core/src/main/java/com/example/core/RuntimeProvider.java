package com.example.core;

import io.micronaut.core.annotation.NonNull;

import java.util.Optional;

public interface RuntimeProvider {
    @NonNull
    Optional<String> runtime();
}
