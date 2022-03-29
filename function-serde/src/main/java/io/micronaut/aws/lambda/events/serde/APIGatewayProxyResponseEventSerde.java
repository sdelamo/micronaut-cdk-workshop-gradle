package io.micronaut.aws.lambda.events.serde;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.SerdeImport;

@Introspected(classes = {
         APIGatewayProxyResponseEvent.class,
 })
@SerdeImport(APIGatewayProxyResponseEvent.class)
final class APIGatewayProxyResponseEventSerde {
}