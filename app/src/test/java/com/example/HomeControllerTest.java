package com.example;
import com.amazonaws.serverless.exceptions.ContainerInitializationException;
import com.amazonaws.serverless.proxy.internal.testutils.AwsProxyRequestBuilder;
import com.amazonaws.serverless.proxy.internal.testutils.MockLambdaContext;
import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.amazonaws.services.lambda.runtime.Context;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpMethod;
import io.micronaut.http.HttpStatus;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import io.micronaut.function.aws.proxy.MicronautLambdaHandler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class HomeControllerTest {

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
    void testHomeController() throws JsonProcessingException {
        AwsProxyRequest request = new AwsProxyRequestBuilder("/", HttpMethod.GET.toString())
                .build();
        AwsProxyResponse response = handler.handleRequest(request, lambdaContext);
        Assertions.assertEquals(HttpStatus.OK.getCode(), response.getStatusCode());
        assertEquals("App Type: Micronaut Application. Hello CDK with Micronaut!, You have hit /", response.getBody());
//        assertNotNull(response.getHeaders());
//        assertEquals("text/plain", response.getHeaders().get(HttpHeaders.CONTENT_TYPE));
    }
}
