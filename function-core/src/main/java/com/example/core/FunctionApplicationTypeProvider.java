package com.example.core;

import io.micronaut.core.annotation.NonNull;
import jakarta.inject.Singleton;

@Singleton
public class FunctionApplicationTypeProvider implements ApplicationTypeProvider {

    @Override
    @NonNull
    public String getApplicationType() {
        return "Function Application for Serverless";
    }
}
