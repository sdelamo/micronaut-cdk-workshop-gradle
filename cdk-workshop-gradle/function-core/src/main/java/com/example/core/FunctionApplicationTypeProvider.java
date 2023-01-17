package com.example;

import io.micronaut.core.annotation.NonNull;
import jakarta.inject.Singleton;
import com.example.core.ApplicationTypeProvider;
@Singleton
public class FunctionApplicationTypeProvider implements ApplicationTypeProvider {

    @Override
    @NonNull
    public String getApplicationType() {
        return "Function Application for Serverless";
    }
}
