package com.example;

import com.example.core.ApplicationTypeProvider;
import io.micronaut.core.annotation.NonNull;
import jakarta.inject.Singleton;
@Singleton
public class DefaultApplicationTypeProvider implements ApplicationTypeProvider {

    @Override
    @NonNull
    public String getApplicationType() {
        return "Micronaut Application";
    }
}
