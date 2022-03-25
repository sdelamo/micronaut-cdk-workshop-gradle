package com.example.core;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.function.aws.MicronautRequestHandler;
import jakarta.inject.Inject;
import java.util.Collections;

@Introspected
public class FunctionHandler extends MicronautRequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    @Inject
    ApplicationTypeProvider applicationTypeProvider;

    @Inject
    RuntimeProvider runtimeProvider;

    @Override
    public APIGatewayProxyResponseEvent execute(APIGatewayProxyRequestEvent request) {
        APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();
        responseEvent.setStatusCode(200);
        responseEvent.setHeaders(Collections.singletonMap("Content-Type", "text/plain"));
        String body = "AOT : " + (AotUtils.runningAot("com.example", this.getClass().getClassLoader()) ? "YES": "NO") +
                " App Type: " + applicationTypeProvider.getApplicationType()
                + runtimeProvider.runtime().map(runtime -> " Runtime: " + runtime).orElse("") +
                ". Hello CDK with Micronaut!, You have hit " + request.getPath();

        responseEvent.setBody(body);
        return responseEvent;
    }
}
