package com.example.core;

import io.micronaut.core.annotation.NonNull;

public interface RuntimeProvider {
    @NonNull
    String runtime();
}
