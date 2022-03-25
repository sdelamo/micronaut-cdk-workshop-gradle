package com.example.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import jakarta.inject.Singleton;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class FunctionHandlerTest {

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
    public void testHandler() {
        APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent();
        request.setPath("/");
        APIGatewayProxyResponseEvent responseEvent = functionHandler.execute(request);
        assertEquals("App Type: Serverless Function Runtime: Java 11 Hello CDK with Micronaut!, You have hit /", responseEvent.getBody());
        assertEquals("text/plain", responseEvent.getHeaders().get("Content-Type"));
        assertEquals(200, responseEvent.getStatusCode().intValue());
    }

    @Singleton
    static class MockApplicationTypeProvider implements ApplicationTypeProvider {


        @Override
        public String getApplicationType() {
            return "Serverless Function";
        }
    }
}
