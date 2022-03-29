package io.micronaut.aws.lambda.events.serde;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.SerdeImport;

@Introspected(classes = {
         APIGatewayProxyRequestEvent.class,
         APIGatewayProxyRequestEvent.ProxyRequestContext.class,
         APIGatewayProxyRequestEvent.RequestIdentity.class
 })
@SerdeImport(APIGatewayProxyRequestEvent.class)
final class APIGatewayProxyRequestEventSerde {
}