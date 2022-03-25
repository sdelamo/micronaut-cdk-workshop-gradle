package com.example;

import com.amazonaws.serverless.exceptions.ContainerInitializationException;
import com.amazonaws.serverless.proxy.internal.testutils.MockLambdaContext;
import com.amazonaws.services.lambda.runtime.Context;
import com.example.core.ApplicationTypeProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.function.aws.proxy.MicronautLambdaHandler;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplicationTypeProviderTest {
    private static MicronautLambdaHandler handler;
    private static Context lambdaContext = new MockLambdaContext();
    private static ObjectMapper objectMapper;

    @BeforeAll
    public static void setupSpec() {
        try {
            handler = new MicronautLambdaHandler();
            objectMapper = handler.getApplicationContext().getBean(ObjectMapper.class);

        } catch (ContainerInitializationException e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    public static void cleanupSpec() {
        handler.getApplicationContext().close();
    }


    @Test
    void beanOfTypeApplicationTypeProviderExists() {
        ApplicationTypeProvider provider = handler.getApplicationContext().getBean(ApplicationTypeProvider.class);
        assertEquals("Micronaut Application", provider.getApplicationType());
    }
}
