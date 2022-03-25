package com.example;

import com.example.core.FunctionHandler;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplicationTypeProviderTest {
    private static FunctionHandler functionHandler;

    @BeforeAll
    public static void setupServer() {
        functionHandler = new FunctionHandler();
    }

    @AfterAll
    public static void stopServer() {
        if (functionHandler != null) {
            functionHandler.getApplicationContext().close();
        }
    }

    @Test
    void beanOfTypeRuntimeProviderExists() {
        ApplicationTypeProvider provider = functionHandler.getApplicationContext().getBean(ApplicationTypeProvider.class);
        assertEquals("Function Application for Serverless", provider.getApplicationType());
    }
}
