package io.micronaut.aws.lambda.events.serde;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.example.core.AotUtils;
import com.example.core.ApplicationTypeProvider;
import com.example.core.RuntimeProvider;
import io.micronaut.core.annotation.Introspected;
import jakarta.inject.Inject;

import java.util.Collections;

@Introspected
public class FunctionHandler extends EventRequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    @Inject
    ApplicationTypeProvider applicationTypeProvider;

    @Inject
    RuntimeProvider runtimeProvider;

    @Override
    public APIGatewayProxyResponseEvent execute(APIGatewayProxyRequestEvent request) {
        APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();
        responseEvent.setStatusCode(200);
        responseEvent.setHeaders(Collections.singletonMap("Content-Type", "text/plain"));
        String body = "AOT : " + (AotUtils.runningAot("io.micronaut.aot.generated", this.getClass().getClassLoader()) ? "YES": "NO") +
                " App Type: " + applicationTypeProvider.getApplicationType()
                + runtimeProvider.runtime().map(runtime -> " Runtime: " + runtime).orElse("") +
                ". Hello CDK with Micronaut!, You have hit " + request.getPath();

        responseEvent.setBody(body);
        return responseEvent;
    }
}
