package com.example;

import com.example.core.FunctionHandler;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import com.example.core.RuntimeProvider;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RuntimeProviderTest {
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
        RuntimeProvider runtimeProvider = functionHandler.getApplicationContext().getBean(RuntimeProvider.class);
        assertEquals("JAVA 11", runtimeProvider.runtime());
    }
}
